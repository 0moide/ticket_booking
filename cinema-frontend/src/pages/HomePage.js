import React, { useState, useEffect } from 'react';
import { filmAPI } from '../services/api';
import FilmCard from '../components/FilmCard';
import LoadingSpinner from '../components/LoadingSpinner';

function HomePage() {
    const [films, setFilms] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        loadFilms();
    }, []);

    const loadFilms = async () => {
        try {
            setLoading(true);
            const response = await filmAPI.getAllFilmsWithSessions();
            setFilms(response.data);
            setError(null);
        } catch (err) {
            console.error('Ошибка загрузки фильмов:', err);
            setError('Не удалось загрузить список фильмов. Убедитесь, что бэкенд запущен на порту 8080');
        } finally {
            setLoading(false);
        }
    };

    if (loading) return <LoadingSpinner />;
    
    if (error) {
        return (
            <div style={{ textAlign: 'center', padding: '50px', color: '#e63946' }}>
                <h2>❌ {error}</h2>
                <button 
                    onClick={loadFilms} 
                    style={{ marginTop: '20px', padding: '10px 20px', cursor: 'pointer' }}
                >
                    Попробовать снова
                </button>
            </div>
        );
    }

    return (
        <div className="home-page">
            <h1 className="main-title">Сейчас в кино</h1>
            <div className="films-grid">
                {films.map(film => (
                    <FilmCard key={film.id} film={film} />
                ))}
            </div>
        </div>
    );
}

export default HomePage;