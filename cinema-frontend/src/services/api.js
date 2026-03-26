import axios from 'axios';

// URL твоего Java бэкенда
const API_BASE_URL = 'http://localhost:8080/api';
const STATIC_BASE_URL = 'http://localhost:8080';

const api = axios.create({
    baseURL: API_BASE_URL,
    headers: {
        'Content-Type': 'application/json',
    },
});

export const filmAPI = {
    // Получить все фильмы с сеансами
    getAllFilmsWithSessions: () => api.get('/films/with-sessions'),
    
    // Получить схему зала по filmId и sessionId
    getSeatsByFilmAndSession: (filmId, sessionId) => 
        api.get(`/films/${filmId}/sessions/${sessionId}/seats`),
    
    // Забронировать несколько мест
    reserveMultipleSeats: (filmId, sessionId, bookingData) => 
        api.post(`/films/${filmId}/sessions/${sessionId}/seats/reserve-multiple`, bookingData),
    
    // Отменить бронирование по ключу
    cancelBooking: (filmId, sessionId, cancelData) => 
        api.post(`/films/${filmId}/sessions/${sessionId}/seats/unreserve-multiple`, cancelData),
};

// Вспомогательная функция для получения полного URL картинки
export const getImageUrl = (path) => {
    if (!path) return '';
    if (path.startsWith('http')) return path;
    const cleanPath = path.startsWith('/') ? path.substring(1) : path;
    return `${STATIC_BASE_URL}/${cleanPath}`;
};

export default api;