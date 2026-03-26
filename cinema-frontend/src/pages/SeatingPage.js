import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { filmAPI } from '../services/api';
import LoadingSpinner from '../components/LoadingSpinner';

function SeatingPage() {
    const { filmId, sessionId } = useParams();
    const [sessionData, setSessionData] = useState(null);
    const [seats, setSeats] = useState([]);
    const [selectedSeats, setSelectedSeats] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [booking, setBooking] = useState({
        userName: '',
        userEmail: ''
    });
    const [cancelKey, setCancelKey] = useState('');
    const [lastBookingKey, setLastBookingKey] = useState(null);

    useEffect(() => {
        loadSeatingData();
    }, [sessionId]);

    const loadSeatingData = async () => {
        try {
            setLoading(true);
            const response = await filmAPI.getSeatsByFilmAndSession(filmId, sessionId);
            const data = response.data;
            
            if (data.error) {
                setError(data.error);
                return;
            }
            
            setSessionData({
                filmId: filmId,
                filmTitle: data.filmTitle || sessionData?.filmTitle || 'Фильм',
                hallNumber: data.hallNumber,
                sessionTime: data.sessionTime
            });
            
            const seatsArray = [];
            if (data.seats) {
                if (typeof data.seats === 'object' && !Array.isArray(data.seats)) {
                    Object.values(data.seats).forEach(seat => {
                        seatsArray.push({
                            id: seat.id || seat.seatNumber,
                            seatNumber: seat.seatNumber,
                            row: seat.row,
                            number: seat.number,
                            status: seat.status
                        });
                    });
                } else if (Array.isArray(data.seats)) {
                    data.seats.forEach(seat => {
                        seatsArray.push(seat);
                    });
                }
            }
            
            seatsArray.sort((a, b) => {
                if (a.row !== b.row) return a.row - b.row;
                return a.number - b.number;
            });
            
            setSeats(seatsArray);
            setError(null);
        } catch (err) {
            console.error('Ошибка загрузки схемы зала:', err);
            setError('Не удалось загрузить схему зала');
        } finally {
            setLoading(false);
        }
    };

    const toggleSeat = (seat) => {
        if (seat.status !== 'Available') return;
        
        setSelectedSeats(prev => {
            if (prev.some(s => s.seatNumber === seat.seatNumber)) {
                return prev.filter(s => s.seatNumber !== seat.seatNumber);
            } else {
                return [...prev, seat];
            }
        });
    };

    const getSeatClass = (seat) => {
        if (selectedSeats.some(s => s.seatNumber === seat.seatNumber)) {
            return 'seat selected';
        }
        switch(seat.status) {
            case 'Available': return 'seat available';
            case 'Reserved': return 'seat reserved';
            case 'Sold': return 'seat sold';
            default: return 'seat unavailable';
        }
    };

    const getSeatStatusText = (status) => {
        switch(status) {
            case 'Available': return 'Свободно';
            case 'Reserved': return 'Забронировано';
            case 'Sold': return 'Продано';
            default: return 'Недоступно';
        }
    };

    const handleBooking = async () => {
        console.log("filmId =", filmId);
        console.log("sessionId =", sessionId);
        console.log("selectedSeats =", selectedSeats);
        
        if (selectedSeats.length === 0) {
            alert('Пожалуйста, выберите места');
            return;
        }
        
        if (!booking.userName.trim()) {
            alert('Введите ваше имя');
            return;
        }
        
        if (!booking.userEmail.trim()) {
            alert('Введите email');
            return;
        }
        
        if (!isValidEmail(booking.userEmail)) {
            alert('Введите корректный email адрес');
            return;
        }

        try {
            setLoading(true);
            const seatNumbers = selectedSeats.map(s => s.seatNumber);
            
            const response = await filmAPI.reserveMultipleSeats(
                filmId, 
                sessionId, 
                {
                    userName: booking.userName,
                    userEmail: booking.userEmail,
                    seatNumbers: seatNumbers
                }
            );
            
            console.log("Ответ от сервера:", response.data);
            
            if (response.data.success) {
                const key = response.data.key;
                if (key) {
                    setLastBookingKey(key);
                    alert(`✅ Бронирование успешно!\n\nКлюч бронирования: ${key}\n\nСохраните этот ключ для отмены бронирования.`);
                } else {
                    alert('✅ Бронирование успешно оформлено! Проверьте почту для получения ключа.');
                }
                
                setSelectedSeats([]);
                setBooking({ userName: '', userEmail: '' });
                loadSeatingData();
            } else {
                alert('❌ Не удалось забронировать места. Возможно, некоторые места уже заняты.');
                loadSeatingData();
            }
        } catch (err) {
            console.error('Ошибка бронирования:', err);
            alert('❌ Произошла ошибка при бронировании');
        } finally {
            setLoading(false);
        }
    };

    const handleCancel = async () => {
        if (!cancelKey.trim()) {
            alert('Введите ключ бронирования');
            return;
        }

        if (!window.confirm('Вы уверены, что хотите отменить бронирование?')) {
            return;
        }

        try {
            setLoading(true);
            
            const response = await filmAPI.cancelBooking(filmId, sessionId, {
                key: cancelKey
            });
            
            if (response.data.success) {
                alert('✅ Бронирование успешно отменено');
                setCancelKey('');
                if (lastBookingKey === cancelKey) {
                    setLastBookingKey(null);
                }
                loadSeatingData();
            } else {
                alert('❌ Не удалось отменить бронирование. Проверьте правильность ключа.');
            }
        } catch (err) {
            console.error('Ошибка отмены:', err);
            alert('❌ Произошла ошибка при отмене бронирования');
        } finally {
            setLoading(false);
        }
    };

    const isValidEmail = (email) => {
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        return emailRegex.test(email);
    };

    const formatSessionTime = (timeString) => {
        if (!timeString) return '';
        const date = new Date(timeString);
        return date.toLocaleString('ru-RU', {
            day: '2-digit',
            month: '2-digit',
            year: 'numeric',
            hour: '2-digit',
            minute: '2-digit'
        });
    };

    const getRowsCount = () => {
        if (!seats.length) return 0;
        const rows = [...new Set(seats.map(s => s.row))];
        return Math.max(...rows);
    };

    const getSeatsPerRow = () => {
        if (!seats.length) return 0;
        const seatsInRow = seats.filter(s => s.row === 1);
        return seatsInRow.length;
    };

    if (loading) return <LoadingSpinner />;
    if (error) return <div className="error" style={{ textAlign: 'center', padding: '50px', color: '#e63946' }}>{error}</div>;
    if (!sessionData) return <div className="error">Нет данных о сеансе</div>;

    const rowsCount = getRowsCount();
    const seatsPerRow = getSeatsPerRow();

    return (
        <div className="seating-page">
            <div className="session-info">
                <h2>{sessionData.filmTitle}</h2>
                <div className="hall-info">
                    <span className="hall-badge">🎬 Зал № {sessionData.hallNumber}</span>
                    <span className="hall-badge">⏰ {formatSessionTime(sessionData.sessionTime)}</span>
                    <span className="hall-badge">🪑 Рядов: {rowsCount}, мест в ряду: {seatsPerRow}</span>
                </div>
            </div>

            <div className="screen">🎬 ЭКРАН 🎬</div>

            <div className="seating-chart">
                {Array(rowsCount).fill().map((_, rowIndex) => {
                    const rowNumber = rowIndex + 1;
                    const seatsInRow = seats.filter(s => s.row === rowNumber);
                    
                    return (
                        <div key={rowNumber} className="row">
                            <div className="row-label">Ряд {rowNumber}</div>
                            {Array(seatsPerRow).fill().map((_, seatIndex) => {
                                const seatNumber = seatIndex + 1;
                                const seat = seatsInRow.find(s => s.number === seatNumber);
                                
                                if (!seat) {
                                    return <div key={seatNumber} className="seat unavailable" style={{ visibility: 'hidden' }}></div>;
                                }
                                
                                return (
                                    <button
                                        key={seat.seatNumber}
                                        className={getSeatClass(seat)}
                                        onClick={() => toggleSeat(seat)}
                                        disabled={seat.status !== 'Available'}
                                        title={`${getSeatStatusText(seat.status)}`}
                                    >
                                        {seat.number}
                                    </button>
                                );
                            })}
                        </div>
                    );
                })}
            </div>

            <div className="legend">
                <div className="legend-item">
                    <div className="legend-color available"></div>
                    <span>Свободно</span>
                </div>
                <div className="legend-item">
                    <div className="legend-color selected"></div>
                    <span>Выбрано</span>
                </div>
                <div className="legend-item">
                    <div className="legend-color reserved"></div>
                    <span>Забронировано</span>
                </div>
                <div className="legend-item">
                    <div className="legend-color sold"></div>
                    <span>Продано</span>
                </div>
            </div>

            <div className="booking-section">
                <div className="booking-card">
                    <h3 className="card-title">📝 Новое бронирование</h3>
                    
                    {selectedSeats.length > 0 && (
                        <div className="selected-seats-info">
                            <h4>Выбранные места:</h4>
                            <p>
                                {selectedSeats.map(seat => `ряд ${seat.row}, место ${seat.number}`).join(' • ')}
                            </p>
                            <p>Всего мест: {selectedSeats.length}</p>
                        </div>
                    )}
                    
                    <div className="form-group">
                        <label>Ваше имя *</label>
                        <input
                            type="text"
                            className="form-control"
                            placeholder="Введите ваше имя"
                            value={booking.userName}
                            onChange={(e) => setBooking({...booking, userName: e.target.value})}
                        />
                    </div>
                    
                    <div className="form-group">
                        <label>Email (для получения ключа) *</label>
                        <input
                            type="email"
                            className="form-control"
                            placeholder="example@mail.com"
                            value={booking.userEmail}
                            onChange={(e) => setBooking({...booking, userEmail: e.target.value})}
                        />
                    </div>
                    
                    <button className="btn" onClick={handleBooking}>
                        ✅ Подтвердить бронирование
                    </button>
                    <button className="btn btn-secondary" onClick={() => setSelectedSeats([])}>
                        ✖ Очистить выбор
                    </button>
                </div>

                <div className="booking-card">
                    <h3 className="card-title">❌ Отмена бронирования</h3>
                    
                    <div className="info-message">
                        <strong>ℹ️ Как отменить бронь:</strong><br />
                        Введите ключ бронирования, который был отправлен на вашу почту
                    </div>
                    
                    <div className="form-group">
                        <label>Ключ бронирования *</label>
                        <input
                            type="text"
                            className="form-control"
                            placeholder="Введите ключ бронирования"
                            value={cancelKey}
                            onChange={(e) => setCancelKey(e.target.value)}
                        />
                    </div>
                    
                    <button className="btn btn-cancel" onClick={handleCancel}>
                        ❌ Отменить бронирование
                    </button>
                    
                    {lastBookingKey && (
                        <div className="booking-key">
                            <strong>Последний ключ бронирования:</strong><br />
                            <span>{lastBookingKey}</span>
                        </div>
                    )}
                </div>
            </div>
        </div>
    );
}

export default SeatingPage;