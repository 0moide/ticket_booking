import React, { useState } from 'react';
import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';
import './DateFilter.css';

function DateFilter({ onDateChange, selectedDate, showSelectedText = false }) {
    const [showCalendar, setShowCalendar] = useState(false);

    const today = new Date();
    today.setHours(0, 0, 0, 0);

    const tomorrow = new Date(today);
    tomorrow.setDate(tomorrow.getDate() + 1);

    const handleToday = () => {
        setShowCalendar(false);
        onDateChange(today);
    };

    const handleTomorrow = () => {
        setShowCalendar(false);
        onDateChange(tomorrow);
    };

    const handleDateSelect = (date) => {
        setShowCalendar(false);
        if (date) {
            const newDate = new Date(date);
            newDate.setHours(0, 0, 0, 0);
            onDateChange(newDate);
        }
    };

    const formatDate = (date) => {
        if (!date) return 'Выбрать день';
        const todayDate = new Date();
        todayDate.setHours(0, 0, 0, 0);
        
        if (date.getTime() === todayDate.getTime()) {
            return 'Сегодня';
        }
        
        const tomorrowDate = new Date(todayDate);
        tomorrowDate.setDate(tomorrowDate.getDate() + 1);
        if (date.getTime() === tomorrowDate.getTime()) {
            return 'Завтра';
        }
        
        return date.toLocaleDateString('ru-RU', {
            day: 'numeric',
            month: 'long'
        });
    };

    // Определяем активную кнопку
    const isTodayActive = selectedDate && formatDate(selectedDate) === 'Сегодня';
    const isTomorrowActive = selectedDate && formatDate(selectedDate) === 'Завтра';
    const isCustomDateActive = selectedDate && !isTodayActive && !isTomorrowActive;

    return (
        <div className="date-filter">
            <button 
                className={`date-btn ${isTodayActive ? 'active' : ''}`}
                onClick={handleToday}
            >
                Сегодня
            </button>
            <button 
                className={`date-btn ${isTomorrowActive ? 'active' : ''}`}
                onClick={handleTomorrow}
            >
                Завтра
            </button>
            <div className="date-picker-wrapper">
                <button 
                    className={`date-btn ${isCustomDateActive ? 'active' : ''}`}
                    onClick={() => setShowCalendar(!showCalendar)}
                >
                    Выбрать день
                </button>
                {showCalendar && (
                    <div className="date-picker-popup">
                        <DatePicker
                            selected={selectedDate}
                            onChange={handleDateSelect}
                            inline
                            minDate={today}
                            onClickOutside={() => setShowCalendar(false)}
                        />
                    </div>
                )}
            </div>
        </div>
    );
}

export default DateFilter;