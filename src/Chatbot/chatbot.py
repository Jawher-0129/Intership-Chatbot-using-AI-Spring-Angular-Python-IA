import random
import json
import pickle
import numpy as np
import nltk
from nltk.corpus import stopwords
from nltk.stem import WordNetLemmatizer
from tensorflow.keras.models import load_model
from tensorflow.keras.preprocessing.sequence import pad_sequences
from tensorflow.keras.preprocessing.text import Tokenizer
from flask import Flask, request, jsonify

# Télécharger les ressources nécessaires
nltk.download('wordnet')
nltk.download('punkt')
nltk.download('stopwords')

lemmatizer = WordNetLemmatizer()
stop_words = set(stopwords.words('french'))

# Charger les données et le modèle
intents = json.loads(open('intents.json').read())
words = pickle.load(open('words.pkl', 'rb'))
classes = pickle.load(open('classes.pkl', 'rb'))
model = load_model('chatbot_model.keras')

# Configurer le tokenizer
tokenizer = Tokenizer(num_words=5000, oov_token="OOV")
tokenizer.fit_on_texts([lemmatizer.lemmatize(word.lower()) for word in words])

# Initialiser un compteur pour les tentatives infructueuses
unknown_count = 0

def clean_up_sentence(sentence):
    sentence_words = nltk.word_tokenize(sentence)
    sentence_words = [lemmatizer.lemmatize(word.lower()) for word in sentence_words if word not in stop_words]
    return sentence_words

def bag_of_words(sentence):
    sentence_words = clean_up_sentence(sentence)
    sequences = tokenizer.texts_to_sequences([sentence_words])
    padded_sequences = pad_sequences(sequences, maxlen=20)
    return padded_sequences

def get_response(intents_list, intents_json):
    global unknown_count
    tag = intents_list[0]['intent']
    
    if tag == 'inconnu':
        unknown_count += 1
        if unknown_count >= 3:
            unknown_count = 0  # Réinitialiser le compteur après avoir affiché le message
            return "Désolé, je n'ai pas compris votre demande. Vous pouvez consulter un technicien pour plus d'aide."
        return "Désolé, je n'ai pas compris votre demande."
    
    unknown_count = 0  # Réinitialiser le compteur si une intention est reconnue
    
    list_of_intents = intents_json['intents']
    for i in list_of_intents:
        if i['tag'] == tag:
            return random.choice(i['responses'])
    
    return "Désolé, je n'ai pas compris votre demande."

def predict_class(sentence):
    bow = bag_of_words(sentence)
    res = model.predict(bow)[0]
    ERROR_THRESHOLD = 0.5
    results = [[i, r] for i, r in enumerate(res) if r > ERROR_THRESHOLD]
    results.sort(key=lambda x: x[1], reverse=True)
    
    if not results:
        return [{'intent': 'inconnu', 'probability': '1.0'}]
    
    return_list = []
    for r in results:
        return_list.append({'intent': classes[r[0]], 'probability': str(r[1])})
    
    return return_list

# Créer l'application Flask
app = Flask(__name__)

@app.route('/chatbot', methods=['POST'])
def chatbot_response():
    data = request.get_json()
    message = data.get("message")
    ints = predict_class(message)
    res = get_response(ints, intents)
    return jsonify({"response": res})

if __name__ == "__main__":
    app.run(port=5000, debug=True)
