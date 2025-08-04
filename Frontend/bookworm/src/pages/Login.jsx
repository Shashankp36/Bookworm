import React, { useState } from "react";
import { Link } from "react-router-dom";

const Login = () => {
  const [isRegistering, setIsRegistering] = useState(false);

  return (
    <div className="min-h-screen w-screen flex items-center justify-center bg-gray-900 text-white px-4">
      <div className="relative w-[768px] h-[500px] bg-gray-800 rounded-2xl overflow-hidden shadow-2xl">
        {/* Form content */}
        <div
          className={`absolute top-0 w-1/2 h-full flex flex-col items-center justify-center px-10 transition-all duration-700 ease-in-out ${
            isRegistering ? "right-0" : "left-0"
          }`}
        >
          <h2 className="text-2xl font-bold mb-6">
            {isRegistering ? "Register" : "Login"}
          </h2>

          <form className="w-72">
            {isRegistering && (
              <>
                <input type="text" placeholder="Full Name" className="input" />
                <input type="email" placeholder="Email" className="input" />
              </>
            )}
            <input type="text" placeholder="Username" className="input" />
            <input type="password" placeholder="Password" className="input" />
            {isRegistering && (
              <input
                type="password"
                placeholder="Confirm Password"
                className="input"
              />
            )}

            <div className="flex justify-between text-sm mb-4">
              {!isRegistering && (
                <>
                  <label className="flex items-center">
                    <input type="checkbox" className="mr-2" />
                    Remember Me
                  </label>
                  <a href="#" className="text-indigo-400 hover:underline">
                    Forgot?
                  </a>
                </>
              )}
            </div>

            <button type="submit" className="btn w-full">
              {isRegistering ? "Register" : "Login"}
            </button>
          </form>
        </div>

        {/* Sliding panel */}
        <div
          className={`absolute top-0 w-1/2 h-full bg-indigo-600 text-white flex flex-col justify-center items-center px-10 transition-all duration-700 ease-in-out z-10 rounded-2xl ${
            isRegistering ? "left-0" : "right-0"
          }`}
        >
          <div className="text-center max-w-[240px]">
            <h2 className="text-2xl font-bold mb-2">
              {isRegistering ? "Welcome Back!" : "New here?"}
            </h2>
            <p className="text-sm mb-4">
              {isRegistering
                ? "Already have an account?"
                : "Don't have an account?"}
            </p>
            <button
              onClick={() => setIsRegistering(!isRegistering)}
              className="bg-white text-indigo-600 font-semibold px-6 py-2 rounded shadow hover:bg-gray-100 transition"
            >
              {isRegistering ? "Login" : "Register"}
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Login;
