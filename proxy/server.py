from flask import Flask, request, jsonify
import requests


app = Flask(__name__)


@app.route("/proxy", methods=["POST"])
def proxy():
    try:
        data = request.get_json()
        url = data.get("url")
        parameters = data.get("parameters", {})
        headers = data.get("headers", {})

        response = requests.get(url, params=parameters, headers=headers)

        return jsonify(response.json())
    except Exception as e:
        print(e)
        return jsonify({"error": str(e)}), 500


if __name__ == "__main__":
    app.run(debug=True)
