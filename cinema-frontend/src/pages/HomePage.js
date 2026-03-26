import React, { useState, useEffect } from 'react';
import { filmAPI } from '../services/api';
import FilmCard from '../components/FilmCard';
import LoadingSpinner from '../components/LoadingSpinner';

function HomePage({ selectedDate, searchTerm, selectedGenre, setGenres }) {
    const [allFilms, setAllFilms] = useState([]);
    const [filteredFilms, setFilteredFilms] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        loadFilms();
    }, []);

    useEffect(() => {
        filterFilms();
    }, [allFilms, selectedDate, searchTerm, selectedGenre]);

    const loadFilms = async () => {
        try {
            setLoading(true);
            const response = await filmAPI.getAllFilmsWithSessions();
            
            const filmsWithDates = response.data.map(film => ({
                ...film,
                sessions: film.sessions.map(session => ({
                    ...session,
                    dateObj: new Date(session.time)
                }))
            }));
            setAllFilms(filmsWithDates);
            
            // Передаём жанры в App через пропс
            const uniqueGenres = [...new Set(filmsWithDates.map(film => film.genre))];
            setGenres(uniqueGenres);
            
            setError(null);
        } catch (err) {
            console.error('Ошибка загрузки фильмов:', err);
            setError('Не удалось загрузить список фильмов');
        } finally {
            setLoading(false);
        }
    };

    const filterFilms = () => {
        let result = [...allFilms];
        
        if (selectedDate) {
            result = result.filter(film => {
                return film.sessions.some(session => {
                    const sessionDate = new Date(session.time);
                    sessionDate.setHours(0, 0, 0, 0);
                    return sessionDate.getTime() === selectedDate.getTime();
                });
            });
        }
        
        if (selectedGenre) {
            result = result.filter(film => film.genre === selectedGenre);
        }
        
        if (searchTerm.trim()) {
            const term = searchTerm.toLowerCase().trim();
            result = result.filter(film => 
                film.title.toLowerCase().includes(term)
            );
        }
        
        setFilteredFilms(result);
    };

    if (loading) return <LoadingSpinner />;
    
    if (error) {
        return (
            <div style={{ textAlign: 'center', padding: '50px', color: '#e63946' }}>
                <h2>❌ {error}</h2>
                <button onClick={loadFilms} style={{ marginTop: '20px', padding: '10px 20px', cursor: 'pointer' }}>
                    Попробовать снова
                </button>
            </div>
        );
    }

    return (
        <div className="home-page">
            <h1 className="main-title">Сейчас в кино</h1>
            
            {filteredFilms.length > 0 ? (
                <>
                    <div className="films-count">
                        Найдено фильмов: {filteredFilms.length}
                    </div>
                    <div className="films-grid">
                        {filteredFilms.map(film => (
                            <FilmCard key={film.id} film={film} />
                        ))}
                    </div>
                </>
            ) : (
                <div className="coming-soon" style={{ margin: '40px auto', maxWidth: '400px' }}>
                    <h3>🎬 Ничего не найдено</h3>
                    <p>Попробуйте изменить параметры поиска или выбрать другую дату</p>
                </div>
            )}
        </div>
    );
}

export default HomePage;