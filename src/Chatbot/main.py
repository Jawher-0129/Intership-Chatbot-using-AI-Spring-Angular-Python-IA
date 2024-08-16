import google.generativeai as genai
import speech_recognition as sr
import pyttsx3
from flask import Flask, request, jsonify

app = Flask(__name__)

# Configurer l'API avec votre clé API
genai.configure(api_key="AIzaSyA9suzHhFhyjfz-7shqQ5om2Q9X26ohzcc")

# Configuration du modèle
generation_config = {
    "temperature": 0.9,
    "top_p": 1,
    "max_output_tokens": 2048,
    "response_mime_type": "text/plain",
}

# Initialisation de la session de chat
model = genai.GenerativeModel(
    model_name="gemini-1.0-pro",
    generation_config=generation_config,
)

chat_session = model.start_chat(history=[])

recognizer = sr.Recognizer()
engine = pyttsx3.init()

def speak(text):
    """Convertit le texte en parole."""
    engine.say(text)
    engine.runAndWait()

@app.route('/chat', methods=['POST'])
def chat():
    data = request.json
    user_message = data.get('message', '')
    mode = data.get('mode', 'écrire')

    if not user_message:
        return jsonify({"error": "Message vide"}), 400

    try:
        # Vérifier si le message contient une phrase spécifique
        if "j'ai besoin d'un consultant pour m'aider" in user_message.lower():
            response_text = "D'accord, vous pouvez consulter le technicien."
        else:
            response = chat_session.send_message(user_message)
            response_text = response.text

        if mode == 'parler': 
            speak(response_text)

        return jsonify({"response": response_text})

    except Exception as e:
        return jsonify({"error": str(e)}), 500

@app.route('/recognize-speech', methods=['GET'])
def recognize_speech():
    with sr.Microphone() as source:
        print("Écoute...")
        audio = recognizer.listen(source)
        try:
            user_message = recognizer.recognize_google(audio, language="fr-FR")
            
            # Envoyer le message à la session de chat et obtenir la réponse
            response = chat_session.send_message(user_message)
            response_text = response.text
            
            return jsonify({"message": user_message, "response": response_text})
        except sr.UnknownValueError:
            return jsonify({"error": "Je n'ai pas compris ce que vous avez dit."}), 400
        except sr.RequestError:
            return jsonify({"error": "Erreur lors de la demande de reconnaissance vocale."}), 500


if __name__ == "__main__":
    app.run(debug=True, host="0.0.0.0", port=5000)
