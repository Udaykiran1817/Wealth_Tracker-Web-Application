import { useEffect, useState, useRef } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import jsPDF from "jspdf";
import html2canvas from "html2canvas";
import Sidebar from "../components/Sidebar";
import { motion } from "framer-motion";

const API_BASE = "http://localhost:8082";

export default function Dashboard() {
  const pan = localStorage.getItem("pan");
  const token = localStorage.getItem("token");
  const navigate = useNavigate();
  const dashboardRef = useRef(null);

  const [darkMode, setDarkMode] = useState(false);
  const [data, setData] = useState({ assets: [], liabilities: [] });
  const [user, setUser] = useState(null);
  const [error, setError] = useState("");

  const formatValue = (value) => (value && value !== "NULL" ? value : "-");

  // Redirect if token/pan missing
  useEffect(() => {
    if (!token || !pan) {
      navigate("/");
    }
  }, [token, pan, navigate]);

  // Load theme on start
  useEffect(() => {
    const savedTheme = localStorage.getItem("theme");
    if (savedTheme === "dark") {
      setDarkMode(true);
    }
  }, []);

  // Session expiry check
  useEffect(() => {
    if (!token) return;

    try {
      const payload = JSON.parse(atob(token.split(".")[1]));
      const expiry = payload.exp * 1000;
      const remaining = expiry - Date.now();

      if (remaining <= 0) {
        localStorage.clear();
        navigate("/");
        return;
      }

      const timer = setTimeout(() => {
        alert("Session expired");
        localStorage.clear();
        navigate("/");
      }, remaining);

      return () => clearTimeout(timer);
    } catch (e) {
      localStorage.clear();
      navigate("/");
    }
  }, [token, navigate]);

  // Axios instance
  const apiInstance = axios.create({
    baseURL: API_BASE,
    headers: token ? { Authorization: `Bearer ${token}` } : {},
  });

  apiInstance.interceptors.response.use(
    (res) => res,
    (err) => {
      if (err.response?.status === 401) {
        localStorage.clear();
        navigate("/");
      }
      return Promise.reject(err);
    }
  );

  const loadDashboard = async () => {
    try {
      const [d, u] = await Promise.all([
        apiInstance.get(`/wealth/dashboard?pan=${pan}`),
        apiInstance.get(`/user/by-pan?pan=${pan}`),
      ]);
      setData(d.data || { assets: [], liabilities: [] });
      setUser(u.data || null);
    } catch (e) {
      console.error(e);
      setError("Unable to load dashboard.");
    }
  };

  useEffect(() => {
    if (token && pan) {
      loadDashboard();
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [token, pan]);

  const toggleTheme = () => {
    const next = !darkMode;
    setDarkMode(next);
    localStorage.setItem("theme", next ? "dark" : "light");
  };

  const handleLogout = () => {
    localStorage.clear();
    navigate("/");
  };

  const exportPDF = async () => {
    if (!dashboardRef.current) return;

    try {
      const canvas = await html2canvas(dashboardRef.current, {
        scale: 2,
        backgroundColor: darkMode ? "#020617" : "#ffffff",
        useCORS: true,
      });

      const imgData = canvas.toDataURL("image/png");
      const pdf = new jsPDF("p", "mm", "a4");
      const pdfWidth = pdf.internal.pageSize.getWidth();
      const pdfHeight = (canvas.height * pdfWidth) / canvas.width;

      pdf.addImage(imgData, "PNG", 0, 0, pdfWidth, pdfHeight);
      pdf.save("WealthDashboard.pdf");
    } catch (err) {
      console.error(err);
      alert("Failed to export PDF");
    }
  };

  const rootClass = darkMode
    ? "dashboard-root dashboard-root--dark"
    : "dashboard-root";

  return (
    <motion.div
      initial={{ opacity: 0, y: 20 }}
      animate={{ opacity: 1, y: 0 }}
      transition={{ duration: 0.4 }}
      className={rootClass}
    >
      <div className="dashboard-container" ref={dashboardRef}>
        {/* HEADER */}
        <header className="dashboard-header">
          <div className="dashboard-header-left">
            <p className="dashboard-kicker">Wealth Snapshot</p>
            <h1 className="dashboard-title">
              Welcome{" "}
              <span className="dashboard-title-highlight">
                {user?.fullName || "Customer"}
              </span>
            </h1>
            <p className="dashboard-subtitle">
              PAN: <span>{user?.panNumber || pan}</span>
            </p>
          </div>

          <Sidebar user={user} darkMode={darkMode} />

          <div className="dashboard-header-right">
            <button className="btn btn-ghost" onClick={toggleTheme}>
              {darkMode ? "☀ Light Mode" : "🌙 Dark Mode"}
            </button>
            <button className="btn btn-primary" onClick={exportPDF}>
              📄 Export PDF
            </button>
            <button className="btn btn-danger" onClick={handleLogout}>
              🚪 Logout
            </button>
          </div>
        </header>

        {error && <div className="dashboard-error">{error}</div>}

        {/* ASSETS */}
        <section className="dashboard-section">
          <div className="section-header">
            <h2 className="section-title">Assets</h2>
            <p className="section-subtitle">
              Your current investments and holdings
            </p>
          </div>

          <div className="table-wrapper">
            <table className="dashboard-table">
              <thead>
                <tr>
                  <th>Mutual Funds</th>
                  <th>Investments</th>
                  <th>Insurance</th>
                  <th>Shares</th>
                  <th>Fixed Deposits</th>
                </tr>
              </thead>
              <tbody>
                {data.assets.length === 0 && (
                  <tr>
                    <td colSpan={5} className="empty-row">
                      No asset records found.
                    </td>
                  </tr>
                )}
                {data.assets.map((a, i) => (
                  <tr key={i}>
                    <td>{formatValue(a.mutualFunds)}</td>
                    <td>{formatValue(a.investments)}</td>
                    <td>{formatValue(a.insurance)}</td>
                    <td>{formatValue(a.shares)}</td>
                    <td>{formatValue(a.fixedDeposits)}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </section>

        {/* LIABILITIES */}
        <section className="dashboard-section">
          <div className="section-header">
            <h2 className="section-title">Liabilities</h2>
            <p className="section-subtitle">
              Your ongoing loans and monthly commitments
            </p>
          </div>

          <div className="table-wrapper">
            <table className="dashboard-table">
              <thead>
                <tr>
                  <th>Home Loan</th>
                  <th>EMI</th>
                  <th>Personal Loan</th>
                </tr>
              </thead>
              <tbody>
                {data.liabilities.length === 0 && (
                  <tr>
                    <td colSpan={3} className="empty-row">
                      No liability records found.
                    </td>
                  </tr>
                )}
                {data.liabilities.map((l, i) => (
                  <tr key={i}>
                    <td>{formatValue(l.homeLoan)}</td>
                    <td>{formatValue(l.emi)}</td>
                    <td>{formatValue(l.personalLoans)}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </section>
      </div>
    </motion.div>
  );
}
