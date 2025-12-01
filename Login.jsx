import { useState } from "react";
import axios from "axios";
import { motion } from "framer-motion";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { useNavigate } from "react-router-dom";

const API_BASE = "http://localhost:8082";

export default function Login() {
  const [aadhaar, setAadhaar] = useState("");
  const [pan, setPan] = useState("");
  const [otp, setOtp] = useState("");
  const [step, setStep] = useState("AADHAAR");
  const [loading, setLoading] = useState(false);
  const [message, setMessage] = useState("");
  const [error, setError] = useState("");

  const navigate = useNavigate();
  const [isVerifyingOtp, setIsVerifyingOtp] = useState(false);

  // ✅ AADHAAR VERIFICATION
  const handleAadhaarSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError("");
    setMessage("");

    try {
      const res = await axios.post(`${API_BASE}/auth/verify-aadhaar`, {
        aadhaarNumber: aadhaar,
      });

      if (res.data.success) {
        setMessage("Aadhaar verified successfully.");
        setStep("PAN");
      }
    } catch (err) {
      setError("Invalid Aadhaar number!");
    } finally {
      setLoading(false);
    }
  };

  // ✅ PAN + SEND OTP
  const handlePanSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError("");
    setMessage("");

    try {
      const res = await axios.post(`${API_BASE}/auth/verify-pan`, {
        aadhaarNumber: aadhaar,
        panNumber: pan,
      });

      if (res.data.success) {
        await axios.post(`${API_BASE}/auth/send-otp`, {
          aadhaarNumber: aadhaar,
          panNumber: pan,
        });

        setMessage("PAN verified Successfully. OTP sent to your phone");
        setStep("OTP");
      }
    } catch (err) {
      setError("PAN verification failed!");
    } finally {
      setLoading(false);
    }
  };

  const handleOtpSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError("");

    try {
      const res = await axios.post(`${API_BASE}/auth/verify-otp`, {
        aadhaarNumber: aadhaar,
        panNumber: pan,
        otp: otp,
      });

      if (res.data.success) {
        localStorage.setItem("token", res.data.data);
        localStorage.setItem("pan", pan);

        navigate("/dashboard");
      }
    } catch (err) {
      setError("Invalid OTP. Try again.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-gradient-to-br from-slate-900 to-slate-800 p-6">
      <motion.div
        initial={{ opacity: 0, y: 30 }}
        animate={{ opacity: 1, y: 0 }}
        transition={{ duration: 0.6 }}
        className="w-full max-w-md"
      >
        <Card className="bg-white/90 shadow-2xl backdrop-blur-xl">
          <CardHeader className="text-center">
            <CardTitle className="text-3xl font-bold text-slate-800">
              Wealth Tracker Pro
            </CardTitle>
          </CardHeader>

          <CardContent className="space-y-5">
            {message && (
              <div className="text-green-600 text-center">{message}</div>
            )}
            {error && <div className="text-red-500 text-center">{error}</div>}

            {step === "AADHAAR" && (
              <form onSubmit={handleAadhaarSubmit} className="space-y-4">
                <Input
                  placeholder="Enter Aadhaar Number"
                  value={aadhaar}
                  onChange={(e) => setAadhaar(e.target.value)}
                  maxLength={12}
                />
                <Button className="w-full">Verify Aadhaar</Button>
              </form>
            )}

            {step === "PAN" && (
              <form onSubmit={handlePanSubmit} className="space-y-4">
                <Input
                  placeholder="Enter PAN Number"
                  value={pan}
                  onChange={(e) => setPan(e.target.value.toUpperCase())}
                />
                <Button className="w-full">Verify PAN</Button>
              </form>
            )}

            {step === "OTP" && (
              <form onSubmit={handleOtpSubmit} className="space-y-4">
                <Input
                  placeholder="Enter OTP"
                  value={otp}
                  onChange={(e) => setOtp(e.target.value)}
                />
                <Button className="w-full" disabled={loading}>
                  {loading ? "Verifying..." : "Verify OTP"}
                </Button>
              </form>
            )}

            {loading && (
              <p className="text-center text-slate-400">Processing...</p>
            )}
          </CardContent>
        </Card>
      </motion.div>
    </div>
  );
}
