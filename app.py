from flask import Flask, request, make_response, jsonify
from flask_restful import Resource, Api
from flask_cors import CORS
from flask_sqlalchemy import SQLAlchemy
from functools import wraps

import jwt
import datetime
import pymysql.cursors

app = Flask(__name__)
api = Api(app)
CORS(app)

# Configuration
app.config['SECRET_KEY'] = "secretmenu"

# Database Configuration
app.config['SQLALCHEMY_DATABASE_URI'] = 'mysql+pymysql://root:CapstoneBisaYuk123@34.101.230.69/my_database'
db = SQLAlchemy(app)


def butuh_token(f):
    @wraps(f)
    def decorator(*args, **kwargs):
        token = request.args.get('datatoken')
        if not token:
            return make_response(jsonify({"msg": "Token is missing!"}), 401)
        try:
            jwt.decode(token, app.config['SECRET_KEY'], algorithms=["HS256"])
        except:
            return make_response(jsonify({"msg": "Invalid Token"}), 401)
        return f(*args, **kwargs)
    return decorator


class RegisterUser(Resource):
    def post(self):
        dataUsername = request.form.get('username')
        dataEmail = request.form.get('email')
        dataPassword = request.form.get('password')

        if dataUsername and dataPassword and dataEmail:
            conn = pymysql.connect(
                host='34.101.230.69',
                user='root',
                password='CapstoneBisaYuk123',
                db='my_database',
                cursorclass=pymysql.cursors.DictCursor
            )
            cursor = conn.cursor()
            cursor.execute("INSERT INTO USER (username, email, password) VALUES (%s, %s, %s)",
                           (dataUsername, dataEmail, dataPassword))
            conn.commit()
            cursor.close()
            conn.close()
            return make_response(jsonify({"msg": "Registration successful"}), 200)
        return jsonify({"msg": "Username, email, or password cannot be empty"})


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
                {"username": user['username'], "exp": datetime.datetime.utcnow() + datetime.timedelta(minutes=10)},
                app.config['SECRET_KEY'], algorithm="HS256"
            )
            return make_response(jsonify({"msg": "Login successful", "token": token}), 200)

        return jsonify({"msg": "Login failed, please try again"})


# Register routes
api.add_resource(RegisterUser, "/api/register", methods=["POST"])
api.add_resource(LoginUser, "/api/login", methods=["POST"])

if __name__ == "__main__":
    app.run(debug=True)