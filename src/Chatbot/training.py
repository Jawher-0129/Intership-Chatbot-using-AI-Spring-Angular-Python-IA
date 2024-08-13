import random
import json
import pickle
import numpy as np
import nltk
from nltk.corpus import stopwords
from nltk.stem import WordNetLemmatizer
from tensorflow.keras.models import Sequential
from tensorflow.keras.layers import Dense, Dropout, LSTM, Embedding
from tensorflow.keras.optimizers import Adam
from tensorflow.keras.preprocessing.text import Tokenizer
from tensorflow.keras.preprocessing.sequence import pad_sequences

# Télécharger les ressources nécessaires
nltk.download('wordnet')
nltk.download('punkt')
nltk.download('stopwords')

lemmatizer = WordNetLemmatizer()
stop_words = set(stopwords.words('french'))
intents = json.loads(open('intents.json').read())

# Préparer les données
words = []
classes = []
documents = []
ignore_letters = ['?', '!', '.', ',']

for intent in intents['intents']:
    for pattern in intent['patterns']:
        word_list = nltk.word_tokenize(pattern)
        words.extend(word_list)
        documents.append((word_list, intent['tag']))
        if intent['tag'] not in classes:
            classes.append(intent['tag'])

words = [lemmatizer.lemmatize(word.lower()) for word in words if word not in ignore_letters and word not in stop_words]
words = sorted(set(words))

classes = sorted(set(classes))
pickle.dump(words, open('words.pkl', 'wb'))
pickle.dump(classes, open('classes.pkl', 'wb'))

# Tokenizer pour créer des séquences
tokenizer = Tokenizer(num_words=5000, oov_token="OOV")
tokenizer.fit_on_texts([lemmatizer.lemmatize(word.lower()) for word in words])
word_index = tokenizer.word_index

sequences = tokenizer.texts_to_sequences([lemmatizer.lemmatize(word.lower()) for word in words])
padded_sequences = pad_sequences(sequences, maxlen=20)

training = []
output_empty = [0] * len(classes)

for document in documents:
    seq = tokenizer.texts_to_sequences([lemmatizer.lemmatize(word.lower()) for word in document[0]])
    padded_seq = pad_sequences(seq, maxlen=20)
    
    output_row = list(output_empty)
    output_row[classes.index(document[1])] = 1
    training.append([padded_seq[0], output_row])

random.shuffle(training)

train_x = np.array([t[0] for t in training])
train_y = np.array([t[1] for t in training])

# Construire le modèle LSTM
model = Sequential()
model.add(Embedding(input_dim=len(word_index) + 1, output_dim=64, input_length=20))
model.add(LSTM(128, return_sequences=True))
model.add(Dropout(0.5))
model.add(LSTM(64))
model.add(Dropout(0.5))
model.add(Dense(len(train_y[0]), activation='softmax'))

model.compile(loss='categorical_crossentropy', optimizer=Adam(learning_rate=0.001), metrics=['accuracy'])

model.fit(train_x, train_y, epochs=200, batch_size=5, verbose=1)
model.save('chatbot_model.keras')
print("Modèle d'entraînement terminé.")
