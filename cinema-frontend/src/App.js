import React, { useState } from 'react';
import { BrowserRouter as Router, Routes, Route, Link, useLocation } from 'react-router-dom';
import HomePage from './pages/HomePage';
import FilmPage from './pages/FilmPage';
import SeatingPage from './pages/SeatingPage';
import DateFilter from './components/DateFilter';
import SearchAndFiltersHeader from './components/SearchAndFiltersHeader';
import './App.css';

function AppContent() {
    const location = useLocation();
    const isHomePage = location.pathname === '/';
    
    const [selectedDate, setSelectedDate] = useState(() => {
        const today = new Date();
        today.setHours(0, 0, 0, 0);
        return today;
    });
    const [searchTerm, setSearchTerm] = useState('');
    const [selectedGenre, setSelectedGenre] = useState('');
    const [genres, setGenres] = useState([]);
    const [availableDates, setAvailableDates] = useState([]); // ← добавили
    
    return (
        <div className="app">
            <header className="header">
                <div className="container header-container">
                    <div className="logo-area">
                        <Link to="/" className="logo">🎬 Кинотеатр CINEMA</Link>
                        <div className="tagline">Лучшие фильмы на большом экране</div>
                    </div>
                    
                    {isHomePage && (
                        <div className="filters-area">
                            <DateFilter 
                                onDateChange={setSelectedDate} 
                                selectedDate={selectedDate}
                                availableDates={availableDates}
                            />
                            <SearchAndFiltersHeader 
                                onSearchChange={setSearchTerm}
                                onGenreChange={setSelectedGenre}
                                genres={genres}
                                selectedGenre={selectedGenre}
                                setGenres={setGenres}
                            />
                        </div>
                    )}
                </div>
            </header>

            <main className="container">
                <Routes>
                    <Route path="/" element={
                        <HomePage 
                            selectedDate={selectedDate}
                            searchTerm={searchTerm}
                            selectedGenre={selectedGenre}
                            setGenres={setGenres}
                            setAvailableDates={setAvailableDates}
                        />
                    } />
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
    );
}

function App() {
    return (
        <Router>
            <AppContent />
        </Router>
    );
}

export default App;