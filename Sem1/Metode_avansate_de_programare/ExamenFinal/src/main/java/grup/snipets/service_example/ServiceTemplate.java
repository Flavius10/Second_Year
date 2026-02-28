package grup.snipets.service_example;

import grup.snipets.repo_example.RepoTemplate;
import grup.utils.Observable; // Sau clasa ta de Observer
// import grup.domain.Item;
// import grup.domain.User;
// import grup.utils.Page;
// import grup.utils.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class ServiceTemplate extends Observable {

    private RepoTemplate repo;

    public ServiceTemplate(RepoTemplate repo) {
        this.repo = repo;
    }

    // ==========================================
    // 1. LOGIN (Esential)
    // ==========================================
//    public User login(String username, String password) {
//        // Presupunem ca ai findByUsername in Repo
//        // Daca nu ai, poti lua findAll() si filtra aici (mai lent, dar merge la examen)
//        return repo.findAll().stream()
//                .filter(u -> u.getUsername().equals(username) && u.getPassword().equals(password))
//                .findFirst()
//                .orElseThrow(() -> new RuntimeException("User sau parola gresita!"));
//    }

    // ==========================================
    // 2. PAGINARE (Standard)
    // ==========================================
//    public Page<Item> getItemsOnPage(int pageNumber, int pageSize) {
//        Pageable pageable = new Pageable(pageNumber, pageSize);
//        return repo.findAllOnPage(pageable);
//    }

    // ==========================================
    // 3. ADAUGARE CU VALIDARE COMPLEXA
    // ==========================================
//    public void addItem(String name, String desc, Double valDouble, String owner) {
//        // Validare
//        if (name == null || name.length() < 3)
//            throw new RuntimeException("Numele trebuie sa aiba minim 3 caractere!");
//
//        // Validare Business (Ex: Nu poti adauga item scump daca esti Junior)
//        if (valDouble > 1000 && owner.contains("JUNIOR")) {
//            throw new RuntimeException("Nu ai dreptul la sume mari!");
//        }
//
//        BigDecimal value = BigDecimal.valueOf(valDouble);
//        Item item = new Item(null, name, desc, value, LocalDateTime.now(), "NEW", owner);
//
//        repo.save(item);
//        notifyObservers(); // Refresh UI
//    }

    // ==========================================
    // 4. STERGERE (Delete)
    // ==========================================
//    public void deleteItem(Long id) {
//        // Optional: Verifici daca exista inainte
//        repo.delete(id);
//        notifyObservers();
//    }

//    public Page<Car> getCarsOnPage(int pageNumber, int pageSize, LocalDate filterDate) {
//        Pageable pageable = new Pageable(pageNumber, pageSize);
//
//        // Aici e logica: Daca avem data, filtram. Daca nu, aducem tot.
//        return repo.findAllOnPageFiltered(pageable, filterDate);
//    }

    // ==========================================
    // 5. FILTRARE SI SORTARE IN MEMORIE (Streams)
    // ==========================================
    // Uneori e mai usor sa iei tot si sa filtrezi in Java decat sa faci SQL complex
//    public List<Item> filterAndSortItems(String searchText, Double minPrice) {
//        return repo.findAll().stream()
//                // 1. Filtrare dupa text (nume sau descriere)
//                .filter(item -> {
//                    if (searchText == null || searchText.isEmpty()) return true;
//                    String lowerText = searchText.toLowerCase();
//                    return item.getName().toLowerCase().contains(lowerText) ||
//                           item.getDescription().toLowerCase().contains(lowerText);
//                })
//                // 2. Filtrare dupa pret
//                .filter(item -> {
//                    if (minPrice == null) return true;
//                    return item.getValue().doubleValue() >= minPrice;
//                })
//                // 3. Sortare (Cele mai noi primele)
//                .sorted(Comparator.comparing(Item::getDateCreated).reversed())
//                .collect(Collectors.toList());
//    }

    // ==========================================
    // 6. STATISTICI (Grouping By)
    // ==========================================
    // Ex: "Cati itemi sunt in fiecare status?" (NEW: 5, APPROVED: 2)
//    public Map<String, Long> getStatisticsByStatus() {
//        return repo.findAll().stream()
//                .collect(Collectors.groupingBy(Item::getStatus, Collectors.counting()));
//    }
//
//    // Ex: "Valoarea totala a itemilor per User"
//    public Map<String, Double> getTotalValuePerUser() {
//        return repo.findAll().stream()
//                .collect(Collectors.groupingBy(
//                        Item::getOwner,
//                        Collectors.summingDouble(item -> item.getValue().doubleValue())
//                ));
//    }

    // ==========================================
    // 7. ASINCRON CU UPDATE UI (Platform.runLater)
    // ==========================================
//    public void update(Long itemId) {
//        new Thread(() -> {
//            try {
//                // Simulam procesare grea (ex: trimitere email)
//                Thread.sleep(2000);
//
//                // Facem update in DB
//                repo.update(itemId, "APPROVED");
//
//                // IMPORTANTA: Codul care atinge GUI trebuie sa fie in runLater
//                javafx.application.Platform.runLater(() -> {
//                    // Aici poti trimite un mesaj specific observatorilor
//                    // notifyObservers(new Event("ITEM_APPROVED", itemId));
//                    notifyObservers();
//                    System.out.println("Item aprobat cu succes!");
//                });
//
//            } catch (Exception e) {
//                e.printStackTrace();
//                javafx.application.Platform.runLater(() -> {
//                   // Poti notifica o eroare (daca ai implementat mecanismul)
//                   System.err.println("Eroare la aprobare: " + e.getMessage());
//                });
//            }
//        }).start();
//    }
}