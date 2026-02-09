package com.example.mywebsite;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class FilmService {
    private List<Film> films = new ArrayList<>();
    private Long currentId = 1L;
    
    public FilmService() {
        // Инициализируем тестовыми данными
        initializeFilms();
    }
    
    private void initializeFilms() {
        films.add(new Film(currentId++, "Дюна: Часть вторая", 
                "Продолжение эпической саги о Полу Атрейдесе.", 
                "Фантастика", 166, 
                "https://images.unsplash.com/photo-1534447677768-be436bb09401?w=400", 16));
        
        films.add(new Film(currentId++, "Оппенгеймер", 
                "История создания атомной бомбы.", 
                "Биография, Драма", 180, 
                "https://images.unsplash.com/photo-1534447677768-be436bb09401?w-400", 18));
        
        films.add(new Film(currentId++, "Безумный Макс: Дорога ярости", 
                "Постапокалиптический боевик в пустыне.", 
                "Боевик", 120, 
                "https://images.unsplash.com/photo-1489599809516-9827b6d1cf13?w=400", 18));
        
        films.add(new Film(currentId++, "Крепкий орешек", 
                "Классический боевик с Брюсом Уиллисом.", 
                "Боевик", 132, 
                "https://images.unsplash.com/photo-1489599809516-9827b6d1cf13?w=400", 16));
    }
    
    public List<Film> getAllFilms() {
        return new ArrayList<>(films);
    }
    
    public Film getFilmById(Long id) {
        return films.stream()
                .filter(f -> f.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}