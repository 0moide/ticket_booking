import React, { useState, useRef, useEffect } from 'react';
import './SearchAndFiltersHeader.css';

function SearchAndFiltersHeader({ onSearchChange, onGenreChange, genres, selectedGenre, setGenres }) {
    const [searchTerm, setSearchTerm] = useState('');
    const [showFilterPopup, setShowFilterPopup] = useState(false);
    const popupRef = useRef(null);
    const buttonRef = useRef(null);

    const handleSearch = (e) => {
        const value = e.target.value;
        setSearchTerm(value);
        onSearchChange(value);
    };

    const handleClear = () => {
        setSearchTerm('');
        onSearchChange('');
    };

    const handleGenreClick = (genre) => {
        if (selectedGenre === genre) {
            onGenreChange('');
        } else {
            onGenreChange(genre);
        }
        setShowFilterPopup(false);
    };

    const handleReset = () => {
        onGenreChange('');
        setShowFilterPopup(false);
    };

    const togglePopup = () => {
        setShowFilterPopup(!showFilterPopup);
    };

    // Закрываем попап при клике вне его
    useEffect(() => {
        const handleClickOutside = (event) => {
            if (popupRef.current && !popupRef.current.contains(event.target) &&
                buttonRef.current && !buttonRef.current.contains(event.target)) {
                setShowFilterPopup(false);
            }
        };
        document.addEventListener('mousedown', handleClickOutside);
        return () => document.removeEventListener('mousedown', handleClickOutside);
    }, []);

    return (
        <div className="header-filters">
            <div className="header-search">
                <input
                    type="text"
                    placeholder="Поиск..."
                    value={searchTerm}
                    onChange={handleSearch}
                    className="header-search-input"
                />
                {searchTerm && (
                    <button className="header-search-clear" onClick={handleClear}>
                        ✕
                    </button>
                )}
            </div>
            
            <div className="header-filter-wrapper">
                <button 
                    ref={buttonRef}
                    className={`filter-btn ${selectedGenre ? 'active' : ''}`}
                    onClick={togglePopup}
                    title="Фильтр по жанру"
                >
                    <svg width="18" height="18" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                        <path d="M4 6H20M6 12H18M10 18H14" stroke="currentColor" strokeWidth="2" strokeLinecap="round"/>
                        <circle cx="8" cy="6" r="2" stroke="currentColor" strokeWidth="2"/>
                        <circle cx="16" cy="12" r="2" stroke="currentColor" strokeWidth="2"/>
                        <circle cx="12" cy="18" r="2" stroke="currentColor" strokeWidth="2"/>
                    </svg>
                    {selectedGenre && <span className="filter-badge"></span>}
                </button>
                
                {showFilterPopup && (
                    <div className="filter-popup" ref={popupRef}>
                        <div className="filter-popup-header">
                            <span>Фильтр по жанру</span>
                            <button className="filter-popup-reset" onClick={handleReset}>
                                Сбросить
                            </button>
                        </div>
                        <div className="filter-popup-genres">
                            <button
                                className={`filter-genre-btn ${selectedGenre === '' ? 'active' : ''}`}
                                onClick={() => {
                                    onGenreChange('');
                                    setShowFilterPopup(false);
                                }}
                            >
                                Все жанры
                            </button>
                            {genres.map(genre => (
                                <button
                                    key={genre}
                                    className={`filter-genre-btn ${selectedGenre === genre ? 'active' : ''}`}
                                    onClick={() => handleGenreClick(genre)}
                                >
                                    {genre}
                                </button>
                            ))}
                        </div>
                    </div>
                )}
            </div>
        </div>
    );
}

export default SearchAndFiltersHeader;