import React from 'react';
import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';
import HomePage from './pages/HomePage';
import FilmPage from './pages/FilmPage';
import SeatingPage from './pages/SeatingPage';
import './App.css';

function App() {
    return (
        <Router>
            <div className="app">
                <header className="header">
                    <div className="container">
                        <Link to="/" className="logo">🎬 Кинотеатр CINEMA</Link>
                        <div className="tagline">Лучшие фильмы на большом экране</div>
                    </div>
                </header>

                <main className="container">
                    <Routes>
                        <Route path="/" element={<HomePage />} />
                        <Route path="/film/:id" element={<FilmPage />} />
                        <Route path="/film/:filmId/session/:sessionId" element={<SeatingPage />} />
                    </Routes>
                </main>

                <footer className="footer">
                    <div className="container">
                        <div className="footer-content">
                            <div className="copyright">
                                © 2026 Кинотеатр CINEMA. Все права защищены.
                            </div>
                            <div className="footer-links">
                                <Link to="#">О кинотеатре</Link>
                                <Link to="#">Правила</Link>
                                <Link to="#">Контакты</Link>
                            </div>
                        </div>
                    </div>
                </footer>
            </div>
        </Router>
    );
}

export default App;