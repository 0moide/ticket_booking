package com.example.mywebsite.services;

import com.example.mywebsite.entities.*;
import com.example.mywebsite.repositories.BookingRepository;
import com.example.mywebsite.repositories.FilmRepository;
import com.example.mywebsite.repositories.SessionRepository;
import com.example.mywebsite.repositories.SeatRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class DatabaseService {
    private final FilmRepository filmRepository;
    private final SessionRepository sessionRepository;
    private final BookingRepository bookingRepository;
    private final SeatRepository seatRepository;
    private Long currentFilmId = 1L;
    private Long currentSessionId = 1L;
    private Long currentBookingId = 1L;
    
    @Autowired
    public DatabaseService(FilmRepository filmRepository, 
                          SessionRepository sessionRepository,
                          BookingRepository bookingRepository,
                          SeatRepository seatRepository) {
        this.filmRepository = filmRepository;
        this.sessionRepository = sessionRepository;
        this.bookingRepository = bookingRepository;
        this.seatRepository = seatRepository;
        initializeFilms();
        addSessions();
    }
    
    @Transactional
    public void initializeFilms() {
        if (filmRepository.count() > 0) {
            return;
        }

        Film film1 = new Film(currentFilmId++, "Интерстеллар", 
                "Фантастический эпос про задыхающуюся Землю, космические полеты и парадоксы времени", 
                "Научная фантастика", 169, 
                "interstellar.jpg", 16);
        filmRepository.save(film1);
        
        Film film2 = new Film(currentFilmId++, "Оппенгеймер", 
                "История создания атомной бомбы.", 
                "Биография, Драма", 180, 
                "oppenheimer.jpg", 18);
        filmRepository.save(film2);
        
        Film film3 = new Film(currentFilmId++, "Человек-бензопила: история Резе", 
                "Новые приключения Дендзи", 
                "Боевик", 100, 
                "chainsaw_man.jpg", 18);
        filmRepository.save(film3);
        
        Film film4 = new Film(currentFilmId++, "Крепкий орешек", 
                "Классический боевик с Брюсом Уиллисом.", 
                "Боевик", 132, 
                "default.jpg", 16);
        filmRepository.save(film4);
    }
    
    @Transactional
    public void addSessions() {
        List<Film> films = filmRepository.findAll();
        
        // Интерстеллар (id = 1)
        Film film1 = films.get(0);
        List<Session> sessions1 = new ArrayList<>();
        
        // Сеанс 15:30
        Session session1 = new Session(currentSessionId++, LocalDateTime.of(2026, 2, 11, 15, 30), null, 1);
        session1.setFilm(film1);
        
        Booking booking1 = new Booking(currentBookingId++, 5, 10);
        booking1.setSession(session1);
        session1.setBooking(booking1);
        
        sessionRepository.save(session1);
        
        // Сохраняем места для booking1
        for (Seat seat : booking1.getSeats()) {
            seat.setBooking(booking1);
            seatRepository.save(seat);
        }
        
        sessions1.add(session1);
        
        // Сеанс 17:30
        Session session2 = new Session(currentSessionId++, LocalDateTime.of(2026, 2, 11, 17, 30), null, 1);
        session2.setFilm(film1);
        
        Booking booking2 = new Booking(currentBookingId++, 5, 10);
        booking2.setSession(session2);
        session2.setBooking(booking2);
        
        sessionRepository.save(session2);
        
        for (Seat seat : booking2.getSeats()) {
            seat.setBooking(booking2);
            seatRepository.save(seat);
        }
        
        sessions1.add(session2);
        
        // Сеанс 19:30
        Session session3 = new Session(currentSessionId++, LocalDateTime.of(2026, 2, 11, 19, 30), null, 1);
        session3.setFilm(film1);
        
        Booking booking3 = new Booking(currentBookingId++, 5, 10);
        booking3.setSession(session3);
        session3.setBooking(booking3);
        
        sessionRepository.save(session3);
        
        for (Seat seat : booking3.getSeats()) {
            seat.setBooking(booking3);
            seatRepository.save(seat);
        }
        
        sessions1.add(session3);
        
        film1.setSessions(sessions1);
        filmRepository.save(film1);
        
        // Оппенгеймер (id = 2)
        Film film2 = films.get(1);
        List<Session> sessions2 = new ArrayList<>();
        
        // Сеанс 15:30
        Session session4 = new Session(currentSessionId++, LocalDateTime.of(2026, 2, 11, 15, 30), null, 2);
        session4.setFilm(film2);
        
        Booking booking4 = new Booking(currentBookingId++, 5, 10);
        booking4.setSession(session4);
        session4.setBooking(booking4);
        
        sessionRepository.save(session4);
        
        for (Seat seat : booking4.getSeats()) {
            seat.setBooking(booking4);
            seatRepository.save(seat);
        }
        
        sessions2.add(session4);
        
        // Сеанс 17:30
        Session session5 = new Session(currentSessionId++, LocalDateTime.of(2026, 2, 11, 17, 30), null, 2);
        session5.setFilm(film2);
        
        Booking booking5 = new Booking(currentBookingId++, 5, 10);
        booking5.setSession(session5);
        session5.setBooking(booking5);
        
        sessionRepository.save(session5);
        
        for (Seat seat : booking5.getSeats()) {
            seat.setBooking(booking5);
            seatRepository.save(seat);
        }
        
        sessions2.add(session5);
        
        // Сеанс 19:30
        Session session6 = new Session(currentSessionId++, LocalDateTime.of(2026, 2, 11, 19, 30), null, 2);
        session6.setFilm(film2);
        
        Booking booking6 = new Booking(currentBookingId++, 5, 10);
        booking6.setSession(session6);
        session6.setBooking(booking6);
        
        sessionRepository.save(session6);
        
        for (Seat seat : booking6.getSeats()) {
            seat.setBooking(booking6);
            seatRepository.save(seat);
        }
        
        sessions2.add(session6);
        
        film2.setSessions(sessions2);
        filmRepository.save(film2);
        
        // Человек-бензопила (id = 3)
        Film film3 = films.get(2);
        List<Session> sessions3 = new ArrayList<>();
        
        // Сеанс 9:30
        Session session7 = new Session(currentSessionId++, LocalDateTime.of(2026, 2, 11, 9, 30), null, 1);
        session7.setFilm(film3);
        
        Booking booking7 = new Booking(currentBookingId++, 5, 10);
        booking7.setSession(session7);
        session7.setBooking(booking7);
        
        sessionRepository.save(session7);
        
        for (Seat seat : booking7.getSeats()) {
            seat.setBooking(booking7);
            seatRepository.save(seat);
        }
        
        sessions3.add(session7);
        
        // Сеанс 11:30
        Session session8 = new Session(currentSessionId++, LocalDateTime.of(2026, 2, 11, 11, 30), null, 1);
        session8.setFilm(film3);
        
        Booking booking8 = new Booking(currentBookingId++, 5, 10);
        booking8.setSession(session8);
        session8.setBooking(booking8);
        
        sessionRepository.save(session8);
        
        for (Seat seat : booking8.getSeats()) {
            seat.setBooking(booking8);
            seatRepository.save(seat);
        }
        
        sessions3.add(session8);
        
        // Сеанс 13:30
        Session session9 = new Session(currentSessionId++, LocalDateTime.of(2026, 2, 11, 13, 30), null, 1);
        session9.setFilm(film3);
        
        Booking booking9 = new Booking(currentBookingId++, 5, 10);
        booking9.setSession(session9);
        session9.setBooking(booking9);
        
        sessionRepository.save(session9);
        
        for (Seat seat : booking9.getSeats()) {
            seat.setBooking(booking9);
            seatRepository.save(seat);
        }
        
        sessions3.add(session9);
        
        film3.setSessions(sessions3);
        filmRepository.save(film3);
        
        // Крепкий орешек (id = 4)
        Film film4 = films.get(3);
        List<Session> sessions4 = new ArrayList<>();
        
        // Сеанс 9:30
        Session session10 = new Session(currentSessionId++, LocalDateTime.of(2026, 2, 11, 9, 30), null, 2);
        session10.setFilm(film4);
        
        Booking booking10 = new Booking(currentBookingId++, 5, 10);
        booking10.setSession(session10);
        session10.setBooking(booking10);
        
        sessionRepository.save(session10);
        
        for (Seat seat : booking10.getSeats()) {
            seat.setBooking(booking10);
            seatRepository.save(seat);
        }
        
        sessions4.add(session10);
        
        // Сеанс 11:30
        Session session11 = new Session(currentSessionId++, LocalDateTime.of(2026, 2, 11, 11, 30), null, 2);
        session11.setFilm(film4);
        
        Booking booking11 = new Booking(currentBookingId++, 5, 10);
        booking11.setSession(session11);
        session11.setBooking(booking11);
        
        sessionRepository.save(session11);
        
        for (Seat seat : booking11.getSeats()) {
            seat.setBooking(booking11);
            seatRepository.save(seat);
        }
        
        sessions4.add(session11);
        
        // Сеанс 13:30
        Session session12 = new Session(currentSessionId++, LocalDateTime.of(2026, 2, 11, 13, 30), null, 2);
        session12.setFilm(film4);
        
        Booking booking12 = new Booking(currentBookingId++, 5, 10);
        booking12.setSession(session12);
        session12.setBooking(booking12);
        
        sessionRepository.save(session12);
        
        for (Seat seat : booking12.getSeats()) {
            seat.setBooking(booking12);
            seatRepository.save(seat);
        }
        
        sessions4.add(session12);
        
        film4.setSessions(sessions4);
        filmRepository.save(film4);
    }

    @Transactional
    public int reserveSeat(Long filmId, Long sessionId, int seatNumber, String userName, String userEmail) {
        Film film = filmRepository.findById(filmId).orElse(null);
        if (film == null) return 0;
        
        List<Session> sessions = film.getSessions();
        if (sessionId >= sessions.size()) return 0;
        Session session = getSessionById(sessionId);
        int key = Integer.parseInt(CodeGenerator.generateNumericCode());
        
        Booking booking = session.getBooking();
        if (booking == null) return 0;
        
        Seat targetSeat = null;
        for (Seat seat : booking.getSeats()) {
            if (seat.getSeatNumber() == seatNumber) {
                targetSeat = seat;
                break;
            }
        }
        
        if (targetSeat == null) return 0;

        if (targetSeat.getStatus() == SeatStatus.Available) {
            targetSeat.setStatus(SeatStatus.Reserved);
            targetSeat.setName(userName);
            targetSeat.setEmail(userEmail);
            seatRepository.save(targetSeat);
            
            return key;
        }
        return 0;
    }

    @Transactional
    public int reserveMultipleSeats(Long filmId, Long sessionId, 
                                    List<Integer> seatNumbers, 
                                    String userName, String userEmail) {
        Film film = filmRepository.findById(filmId).orElse(null);
        if (film == null) return 0;
        
        Session session = getSessionById(sessionId);
        if (session == null) return 0;
        Booking booking = session.getBooking();
        
        List<Seat> seatsToReserve = new ArrayList<>();
        List<String> seatsInfo = new ArrayList<>();
        int key = Integer.parseInt(CodeGenerator.generateNumericCode());
        
        for (int seatNumber : seatNumbers) {
            for (Seat seat : booking.getSeats()) {
                if (seat.getSeatNumber() == seatNumber && seat.getStatus() == SeatStatus.Available) {
                    seat.setStatus(SeatStatus.Reserved);
                    seat.setName(userName);
                    seat.setEmail(userEmail);
                    seat.setKey(key);
                    seatsToReserve.add(seat);
                    seatsInfo.add(String.format("%d ряд, %d место", seat.getRow(), seat.getNumber()));
                    break;
                }
            }
        }
        
        if (!seatsToReserve.isEmpty()) {
            seatRepository.saveAll(seatsToReserve);
            return key;
        }
        return 0;
    }

    @Transactional
    public String unreserveMultipleSeats(Long sessionId, String key){
        int digitalKey = 0;
        try{
            digitalKey = Integer.parseInt(key);
        }
        catch (Exception e){
            return "";
        }

        Session session = getSessionById(sessionId);
        if (session == null) return "";
        
        Booking booking = session.getBooking();
        List<Seat> seatsToUnreserve = new ArrayList<>();
        String userEmail = "";

        for (Seat seat : booking.getSeats()) {
                if (seat.getKey() == digitalKey && seat.getStatus() == SeatStatus.Reserved) {
                    seat.setStatus(SeatStatus.Available);
                    seatsToUnreserve.add(seat);

                    userEmail = seat.getEmail();
                }
        }
        if (!seatsToUnreserve.isEmpty()) {
            seatRepository.saveAll(seatsToUnreserve);
            return userEmail;
        }
        return "";
    }
    
    @Transactional
    public void resetDatabase() {
        bookingRepository.deleteAll();
        sessionRepository.deleteAll();
        filmRepository.deleteAll();
    }

    public List<Film> getAllFilms() {
        return new ArrayList<>(filmRepository.findAll());
    }
    
    public Film getFilmById(Long id) {
        return filmRepository.findById(id).orElse(null);
    }

    public String getSessionTime(Long sessionId){
        Session session = getSessionById(sessionId);
        if (session == null) return "";
        return session.getTime().toString();
    }

    public int getHallNumber(Long sessionId){
        Session session = getSessionById(sessionId);
        if (session == null) return -1;
        return session.getHallNumber();
    }

    public int getSeatRow(Long sessionId, int seatNumber){
        Session session = getSessionById(sessionId);
        if (session == null) return -1;
        List<Seat> seats = session.getBooking().getSeats();
        for(Seat i : seats){
            if(i.getSeatNumber() == seatNumber){
                return i.getRow();
            }
        }
        return -1;
    }

    public int getSeatNumber(Long sessionId, int seatNumber){
        Session session = getSessionById(sessionId);
        if (session == null) return -1;
        List<Seat> seats = session.getBooking().getSeats();
        for(Seat i : seats){
            if(i.getSeatNumber() == seatNumber){
                return i.getNumber();
            }
        }
        return -1;
    }

    public Session getSessionById(Long sessionId){
        Session session = sessionRepository.findById(sessionId).orElse(null);
        return session;
    }
}