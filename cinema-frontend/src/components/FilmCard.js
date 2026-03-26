import React from 'react';
import { Link } from 'react-router-dom';
import { getImageUrl } from '../services/api';

function FilmCard({ film }) {
    return (
        <div className="film-card">
            <div className="card-header">
                <img 
                    src={getImageUrl(film.posterUrl)} 
                    alt={film.title} 
                    className="film-poster" 
                />
                <div className="age-badge">{film.minAge}+</div>
            </div>
            <div className="film-info">
                <h3 className="film-title">{film.title}</h3>
                <div className="film-meta">
                    <span>{film.duration} мин</span>
                    <span className="film-genre">{film.genre}</span>
                </div>
                <p className="film-description">{film.description}</p>
                <Link to={`/film/${film.id}`} className="btn">
                    Выбрать сеанс
                </Link>
            </div>
        </div>
    );
}

export default FilmCard;