import React, { useState, useEffect } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import { useAuth } from "../contexts/AuthContext";
import SlidingPopup from "../components/SlidingPopup";

const Login = () => {
  const location = useLocation();
  const [isRegistering, setIsRegistering] = useState(false);
  const [errors, setErrors] = useState({});
  const [showPassword, setShowPassword] = useState(false);
  const [popup, setPopup] = useState(null);
  const navigate = useNavigate();
  const { loginUser } = useAuth();

  useEffect(() => {
    if (location.state?.formType === "register") {
      setIsRegistering(true);
    }
  }, [location.state]);

  const validateForm = (data) => {
    const newErrors = {};

    if (isRegistering) {
      if (!data.fullName || data.fullName.length < 3) {
        newErrors.fullName = "Full name must be at least 3 characters.";
      }

      if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(data.email)) {
        newErrors.email = "Invalid email format.";
      }

      if (!/^[0-9]{10}$/.test(data.phone)) {
        newErrors.phone = "Phone number must be 10 digits.";
      }

      if (!data.address || data.address.length < 5) {
        newErrors.address = "Address must be at least 5 characters.";
      }

      if (!data.password || data.password.length < 6) {
        newErrors.password = "Password must be at least 6 characters.";
      }

      if (data.password !== data.confirmPassword) {
        newErrors.confirmPassword = "Passwords do not match.";
      }
    } else {
      if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(data.email)) {
        newErrors.email = "Invalid email format.";
      }

      if (!data.password || data.password.length < 6) {
        newErrors.password = "Password must be at least 6 characters.";
      }
    }

    return newErrors;
  };

  const handleBlur = (e) => {
    const { name, value } = e.target;
    const data = { [name]: value.trim() };
    const validationErrors = validateForm(data);

    if (validationErrors[name]) {
      setErrors((prev) => ({ ...prev, [name]: validationErrors[name] }));
    } else {
      setErrors((prev) => {
        const updated = { ...prev };
        delete updated[name];
        return updated;
      });
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const form = e.target;
    const formData = new FormData(form);
    const data = {};

    for (const [key, value] of formData.entries()) {
      data[key] = value.trim();
    }

    const validationErrors = validateForm(data);
    setErrors(validationErrors);
    if (Object.keys(validationErrors).length > 0) return;

    if (isRegistering) {
      const signupPayload = {
        userName: data.fullName,
        userEmail: data.email,
        userPhone: data.phone,
        userAddress: data.address,
        userPassword: data.password,
        role: "USER",
      };

      try {
        const res = await fetch("http://localhost:8080/api/auth/signup", {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify(signupPayload),
        });

        const result = await res.text();
        console.log(result);
        setPopup({ message: "Registered successfully!", type: "success" });
        setIsRegistering(false);
      } catch (err) {
        console.error("Signup error:", err);
        setPopup({ message: "Signup failed!", type: "error" });
      }
    } else {
      const loginPayload = {
        email: data.email,
        password: data.password,
      };

      try {
        const res = await fetch("http://localhost:8080/api/auth/login", {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify(loginPayload),
        });

        const result = await res.text();
        console.log(result);

        if (res.ok) {
          const parsedResult = JSON.parse(result);
          localStorage.setItem("accessToken", parsedResult.accessToken);
          localStorage.setItem("refreshToken", parsedResult.refreshToken);
          localStorage.setItem("role", parsedResult.role);
          localStorage.setItem("loginTime", Date.now());

          setPopup({ message: "Logged in successfully!", type: "success" });
          loginUser();
          setTimeout(() => navigate("/"), 1000);
        } else {
          setPopup({ message: "Invalid Email or Password", type: "error" });
        }
      } catch (err) {
        console.error("Login error:", err);
        setPopup({ message: "Login failed!", type: "error" });
      }
    }
  };

  return (
    <div className="min-h-screen w-screen flex items-center justify-center bg-gray-300 text-white px-4">
      <div className="relative w-[900px] h-[650px] bg-gray-800 rounded-2xl overflow-hidden shadow-2xl">
        {/* Form Panel */}
        <div className={`absolute top-0 w-1/2 h-full flex flex-col items-center justify-center px-10 transition-all duration-700 ease-in-out ${isRegistering ? "right-0" : "left-0"}`}>
          <h2 className="text-2xl font-bold mb-10">{isRegistering ? "Register" : "Login"}</h2>
          <form className="w-82" onSubmit={handleSubmit} noValidate>
            {isRegistering && (
              <>
                <input type="text" name="fullName" placeholder="Full Name" className="input mb-3" required onBlur={handleBlur} />
                {errors.fullName && <p className="text-red-500 text-sm mb-2">{errors.fullName}</p>}
                <input type="email" name="email" placeholder="Email" className="input mb-3 " required onBlur={handleBlur} />
                {errors.email && <p className="text-red-500 text-sm mb-2">{errors.email}</p>}
                <input type="text" name="phone" placeholder="Phone" className="input mb-3" required onBlur={handleBlur} />
                {errors.phone && <p className="text-red-500 text-sm mb-2">{errors.phone}</p>}
                <input type="text" name="address" placeholder="Address" className="input mb-3" required onBlur={handleBlur} />
                {errors.address && <p className="text-red-500 text-sm mb-2">{errors.address}</p>}
              </>
            )}

            {!isRegistering && (
              <>
                <input type="email" name="email" placeholder="Email" className="input mb-3 w-80" required onBlur={handleBlur} />
                {errors.email && <p className="text-red-500 text-sm mb-2">{errors.email}</p>}
              </>
            )}

            <div className="relative">
              <input type={showPassword ? "text" : "password"} name="password" placeholder="Password" className="input mb-3" required onBlur={handleBlur} />
              <span onClick={() => setShowPassword(!showPassword)} className="absolute top-2 right-4 text-sm cursor-pointer text-gray-300">
                {showPassword ? "🙈" : "👁️"}
              </span>
            </div>
            {errors.password && <p className="text-red-500 text-sm mb-2">{errors.password}</p>}

            {isRegistering && (
              <>
                <input type="password" name="confirmPassword" placeholder="Confirm Password" className="input mb-14" required onBlur={handleBlur} />
                {errors.confirmPassword && <p className="text-red-500 text-sm mb-2">{errors.confirmPassword}</p>}
              </>
            )}

            {!isRegistering && (
              <div className="flex justify-between text-sm mb-6 ">
                <label className="flex items-center">
                  <input type="checkbox" className="mr-6" />
                  Remember Me
                </label>
                <a href="#" className="text-white-400 hover:underline">Forgot?</a>
              </div>
            )}

           <button
  type="submit"
  className="w-full mb-8 bg-indigo-600 hover:bg-indigo-700 text-white font-semibold py-2 px-4 rounded transition"
>
  {isRegistering ? "Register" : "Login"}
</button>

          </form>
        </div>

        {/* Sliding panel */}
        <div className={`absolute top-0 w-1/2 h-full bg-[#B7BBC7] text-black flex flex-col justify-center items-center px-10 transition-all duration-700 ease-in-out z-10 rounded-2xl ${isRegistering ? "left-0" : "right-0"}`}>
          <div className="text-center max-w-[240px]">
            <h2 className="text-3xl font-bold mb-2">{isRegistering ? "New here?" : "Welcome Back!"}</h2>
            <p className="text-sm mb-4">{isRegistering ? "Already have an account?" : "Don't have an account?"}</p>
            <button onClick={() => { setIsRegistering(!isRegistering); setErrors({}); }} className="bg-white text-indigo-600 font-semibold px-6 py-2 rounded-xl shadow hover:bg-gray-100 transition">
              {isRegistering ? "Login" : "Register"}
            </button>
          </div>
        </div>
      </div>

      {/* Popup */}
      {popup && (
        <SlidingPopup message={popup.message} type={popup.type} onClose={() => setPopup(null)} />
      )}
    </div>
  );
};

export default Login;