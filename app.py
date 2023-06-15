from flask import Flask, request, jsonify, make_response
from flask_restful import Resource, Api
from flask_cors import CORS
from flask_sqlalchemy import SQLAlchemy
from functools import wraps
from werkzeug.wrappers import Response

import re
import jwt
import datetime
import os
import pymysql.cursors

app = Flask(__name__)
api = Api(app)
CORS(app)

# Configuration
app.config['SECRET_KEY'] = "secretmenu"

# Database Configuration
app.config['SQLALCHEMY_DATABASE_URI'] = 'mysql+pymysql://root:CapstoneBisaYuk123@34.101.230.69/my_database'
db = SQLAlchemy(app)


def get_user_id_from_token():
    token = request.headers.get('Authorization')  # Mengambil token dari header Authorization
    if token:
        token = token.split(' ')[1]  # Mengambil token setelah kata "Bearer"
        decoded_token = jwt.decode(token, app.config['SECRET_KEY'], algorithms=["HS256"])
        user_id = decoded_token['user_id']  # Mengambil ID pengguna dari token
        return user_id
    else:
        return None


def requires_token(f):
    @wraps(f)
    def decorator(*args, **kwargs):
        token = request.headers.get('Authorization')
        if not token:
            return make_response(jsonify({"error": "Token is missing!"}), 401)
        try:
            token = token.split(' ')[1]
            jwt.decode(token, app.config['SECRET_KEY'], algorithms=["HS256"])
        except:
            return make_response(jsonify({"error": "Invalid Token"}), 401)
        return f(*args, **kwargs)
    return decorator


class RegisterUser(Resource):
    def post(self):
        dataUsername = request.form.get('username')
        dataEmail = request.form.get('email')
        dataPassword = request.form.get('password')

        if dataUsername and dataPassword and dataEmail:
            # Cek format email menggunakan regular expression
            if not re.match(r"[^@]+@[^@]+\.[^@]+", dataEmail):
                return make_response(jsonify({"error": "Invalid email format"}), 400)

            conn = pymysql.connect(
                host='34.101.230.69',
                user='root',
                password='CapstoneBisaYuk123',
                db='my_database',
                cursorclass=pymysql.cursors.DictCursor
            )
            cursor = conn.cursor()
            
            # Cek apakah username atau email sudah ada di database
            cursor.execute("SELECT * FROM USER WHERE username = %s OR email = %s", (dataUsername, dataEmail))
            user = cursor.fetchone()
            
            if user:
                return make_response(jsonify({"error": "Username or email already exists"}), 400)
            
            # Melanjutkan proses registrasi jika username dan email tersedia
            cursor.execute("INSERT INTO USER (username, email, password) VALUES (%s, %s, %s)",
                           (dataUsername, dataEmail, dataPassword))
            conn.commit()
            cursor.close()
            conn.close()
            return make_response(jsonify({"message": "Registration successful"}), 200)
        return make_response(jsonify({"error": "Username, email, or password cannot be empty"}), 400)


class LoginUser(Resource):
    def post(self):
        dataUsername = request.form.get('username')
        dataPassword = request.form.get('password')

        conn = pymysql.connect(
            host='34.101.230.69',
            user='root',
            password='CapstoneBisaYuk123',
            db='my_database',
            cursorclass=pymysql.cursors.DictCursor
        )
        cursor = conn.cursor()
        cursor.execute("SELECT * FROM USER WHERE username = %s AND password = %s", (dataUsername, dataPassword))
        user = cursor.fetchone()
        cursor.close()
        conn.close()

        if user:
            # Login successful, generate token
            token = jwt.encode(
                {"username": user['username'], "user_id": user['id'], "exp": datetime.datetime.utcnow() + datetime.timedelta(minutes=10)},
                app.config['SECRET_KEY'], algorithm="HS256"
            )
            return make_response(jsonify({"message": "Login successful", "token": token}), 200)

        return make_response(jsonify({"error": "Login failed, please try again"}), 400)




class UploadProfilePicture(Resource):
    @requires_token
    def post(self):
        # Cek apakah permintaan mengandung file foto
        if 'foto_profil' not in request.files:
            return make_response(jsonify({"error": "No Profile Picture"}), 400)

        # Dapatkan file foto
        photo = request.files['foto_profil']

        # Simpan file foto
        if photo and photo.filename != '':
            # Tentukan folder tempat foto harus disimpan
            upload_folder = os.path.abspath('uploads')
            os.makedirs(upload_folder, exist_ok=True)
            filename = os.path.join(upload_folder, photo.filename)
            photo.save(filename)

            # Dapatkan user ID dari token
            user_id = get_user_id_from_token()

            if user_id is not None:
                conn = pymysql.connect(
                    host='34.101.230.69',
                    user='root',
                    password='CapstoneBisaYuk123',
                    db='my_database',
                    cursorclass=pymysql.cursors.DictCursor
                )
                cursor = conn.cursor()
                cursor.execute("UPDATE USER SET foto_profil = %s WHERE id = %s", (filename, user_id))
                conn.commit()
                cursor.close()
                conn.close()
                return make_response(jsonify({"message": "Upload success"}), 200)
            else:
                return make_response(jsonify({"error": "Invalid token"}), 401)

        return make_response(jsonify({"error": "No Profile Picture"}), 400)


# Register routes
api.add_resource(RegisterUser, "/api/registrasi", methods=["POST"])
api.add_resource(LoginUser, "/api/signin", methods=["POST"])
api.add_resource(UploadProfilePicture, "/api/upload-profile", methods=["POST"]) # key [body]-form data : foto_profil lalu pilih file gambarnya & key [header] : Authorization lalu paste tokenya

if __name__ == "__main__":
    app.run(debug=True)
