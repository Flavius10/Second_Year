package grup.snipets.repo_example;

//import grup.domain.Item;

// IMPORTA CLASA TA DE DOMENIU AICI
// import grup.domain.Item;
// PENTRU TEMPLATE FOLOSIM O CLASA FICTIVA "Item" CA EXEMPLU

public class RepoTemplate {

    private String url;
    private String username;
    private String password;

    public RepoTemplate(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    // ==========================================
    // 1. EXTRACT ENTITY (AICI MODIFICI LA EXAMEN)
    // ==========================================
//    private Item extractEntity(ResultSet rs) throws SQLException {
//        // --- 1. ID ---
//        Long id = rs.getLong("id");
//
//        // --- 2. STRING-URI ---
//        String name = rs.getString("name");
//        String description = rs.getString("description");
//
//        // --- 3. BANI (BigDecimal) ---
//        BigDecimal value = rs.getBigDecimal("value");
//
//        // --- 4. DATA SI ORA (LocalDateTime) ---
//        Timestamp ts = rs.getTimestamp("date_created");
//        LocalDateTime date = (ts != null) ? ts.toLocalDateTime() : null;
//
//        // --- 5. ALTELE ---
//        String status = rs.getString("status");
//
//        // Returnezi obiectul tau
//        return new Item(id, name, description, value, date, status);
//    }
//
//    // ==========================================
//    // 2. PAGINARE (FIND ALL ON PAGE)
//    // ==========================================
//    public Page<Item> findAllOnPage(Pageable pageable) {
//        // A. Numar total de elemente
//        String countSql = "SELECT COUNT(*) as count FROM items"; // <--- SCHIMBA TABELUL
//
//        // B. Elementele de pe pagina curenta
//        String querySql = "SELECT * FROM items LIMIT ? OFFSET ?"; // <--- SCHIMBA TABELUL
//
//        try (Connection connection = DriverManager.getConnection(url, username, password)) {
//
//            // Pas 1: Aflam Totalul
//            int total = 0;
//            try (PreparedStatement ps = connection.prepareStatement(countSql);
//                 ResultSet rs = ps.executeQuery()) {
//                if (rs.next()) {
//                    total = rs.getInt("count");
//                }
//            }
//
//            // Pas 2: Luam lista paginata
//            List<Item> items = new ArrayList<>();
//            try (PreparedStatement ps = connection.prepareStatement(querySql)) {
//                ps.setInt(1, pageable.getPageSize()); // LIMIT
//                ps.setInt(2, pageable.getPageNumber() * pageable.getPageSize()); // OFFSET
//
//                try (ResultSet rs = ps.executeQuery()) {
//                    while (rs.next()) {
//                        items.add(extractEntity(rs));
//                    }
//                }
//            }
//            return new Page<>(items, total);
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }

    // !!!ASTA SE FACE ATUNCI CAND AI NEVOIE DE CEVA SUB SAU PESTE O DATA
//    public Page<Car> findAllOnPageFiltered(Pageable pageable, LocalDate minDate) {
//        // 1. Daca nu e selectata nicio data, ne comportam normal (aducem tot)
//        if (minDate == null) {
//            return findAllOnPage(pageable);
//        }
//
//        // 2. Query cu filtrare: Doar masinile create DUPA data aleasa
//        String countSql = "SELECT COUNT(*) as count FROM items WHERE date_created >= ?";
//        String querySql = "SELECT * FROM items WHERE date_created >= ? LIMIT ? OFFSET ?";
//
//        try (Connection connection = DriverManager.getConnection(url, username, password)) {
//
//            // A. Aflam totalul filtrat
//            int total = 0;
//            try (PreparedStatement ps = connection.prepareStatement(countSql)) {
//                // Convertim LocalDate la Timestamp (inceputul zilei)
//                ps.setTimestamp(1, Timestamp.valueOf(minDate.atStartOfDay()));
//
//                try (ResultSet rs = ps.executeQuery()) {
//                    if (rs.next()) total = rs.getInt("count");
//                }
//            }
//
//            // B. Luam pagina filtrata
//            List<Car> items = new ArrayList<>();
//            try (PreparedStatement ps = connection.prepareStatement(querySql)) {
//                ps.setTimestamp(1, Timestamp.valueOf(minDate.atStartOfDay()));
//                ps.setInt(2, pageable.getPageSize());
//                ps.setInt(3, pageable.getPageNumber() * pageable.getPageSize());
//
//                try (ResultSet rs = ps.executeQuery()) {
//                    while (rs.next()) {
//                        items.add(extractEntity(rs));
//                    }
//                }
//            }
//            return new Page<>(items, total);
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    // ==========================================
//    // 3. FIND ONE (Optional)
//    // ==========================================
//    public Optional<Item> findOne(Long id) {
//        String sql = "SELECT * FROM items WHERE id = ?"; // <--- SCHIMBA TABELUL
//
//        try (Connection conn = DriverManager.getConnection(url, username, password);
//             PreparedStatement ps = conn.prepareStatement(sql)) {
//
//            ps.setLong(1, id);
//
//            try (ResultSet rs = ps.executeQuery()) {
//                if (rs.next()) {
//                    return Optional.of(extractEntity(rs));
//                }
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        return Optional.empty();
//    }
//
//    // ==========================================
//    // 4. FIND ALL (Simplu)
//    // ==========================================
//    public List<Item> findAll() {
//        String sql = "SELECT * FROM items"; // <--- SCHIMBA TABELUL
//        List<Item> list = new ArrayList<>();
//
//        try (Connection conn = DriverManager.getConnection(url, username, password);
//             PreparedStatement ps = conn.prepareStatement(sql);
//             ResultSet rs = ps.executeQuery()) {
//
//            while (rs.next()) {
//                list.add(extractEntity(rs));
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        return list;
//    }
//
//    // ==========================================
//    // 5. SAVE (Insert)
//    // ==========================================
//    public void save(Item item) {
//        // <--- SCHIMBA SQL-ul
//        String sql = "INSERT INTO items (name, description, value, date_created, status) VALUES (?, ?, ?, ?, ?)";
//
//        try (Connection connection = DriverManager.getConnection(url, username, password);
//             PreparedStatement ps = connection.prepareStatement(sql)) {
//
//            // Seteaza parametrii
//            ps.setString(1, item.getName());
//            ps.setString(2, item.getDescription());
//            ps.setBigDecimal(3, item.getValue()); // BigDecimal
//
//            if (item.getDateCreated() != null)
//                ps.setTimestamp(4, Timestamp.valueOf(item.getDateCreated())); // LocalDateTime
//            else
//                ps.setTimestamp(4, null);
//
//            ps.setString(5, item.getStatus());
//
//            ps.executeUpdate();
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    // ==========================================
//    // 6. DELETE
//    // ==========================================
//    public void delete(Long id) {
//        String sql = "DELETE FROM items WHERE id = ?"; // <--- SCHIMBA TABELUL
//        try (Connection connection = DriverManager.getConnection(url, username, password);
//             PreparedStatement ps = connection.prepareStatement(sql)) {
//
//            ps.setLong(1, id);
//            ps.executeUpdate();
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    // ==========================================
//    // 7. UPDATE
//    // ==========================================
//    public void updateStatus(Long id, String newStatus) {
//        String sql = "UPDATE items SET status = ? WHERE id = ?"; // <--- SCHIMBA TABELUL
//        try (Connection connection = DriverManager.getConnection(url, username, password);
//             PreparedStatement ps = connection.prepareStatement(sql)) {
//
//            ps.setString(1, newStatus);
//            ps.setLong(2, id);
//            ps.executeUpdate();
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }

    // ==========================================
    // 8. FIND BY DATE RANGE (Intre doua date)
    // ==========================================
    // Util pentru rapoarte: "Toate comenzile din luna Ianuarie"
//    public List<Item> findByDateBetween(LocalDateTime start, LocalDateTime end) {
//        String sql = "SELECT * FROM items WHERE date_created >= ? AND date_created <= ?";
//
//        List<Item> list = new ArrayList<>();
//
//        try (Connection conn = DriverManager.getConnection(url, username, password);
//             PreparedStatement ps = conn.prepareStatement(sql)) {
//
//            ps.setTimestamp(1, Timestamp.valueOf(start));
//            ps.setTimestamp(2, Timestamp.valueOf(end));
//
//            try (ResultSet rs = ps.executeQuery()) {
//                while (rs.next()) {
//                    list.add(extractEntity(rs));
//                }
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        return list;
//    }

    // ==========================================
    // 9. FIND BY STRING PARTIAL (Search / Like)
    // ==========================================
    // Cauta elemente care contin un text in nume (Case Insensitive in PostgreSQL -> ILIKE)
//    public List<Item> searchByName(String text) {
//        // Daca folosesti PostgreSQL: "SELECT * FROM items WHERE name ILIKE ?"
//        // Daca folosesti MySQL/H2: "SELECT * FROM items WHERE LOWER(name) LIKE LOWER(?)"
//        String sql = "SELECT * FROM items WHERE name LIKE ?";
//
//        List<Item> list = new ArrayList<>();
//
//        try (Connection conn = DriverManager.getConnection(url, username, password);
//             PreparedStatement ps = conn.prepareStatement(sql)) {
//
//            // Adaugam % pentru a cauta partial (ex: %mere% gaseste "Ana are mere")
//            ps.setString(1, "%" + text + "%");
//
//            try (ResultSet rs = ps.executeQuery()) {
//                while (rs.next()) {
//                    list.add(extractEntity(rs));
//                }
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        return list;
//    }

    // ==========================================
    // 10. DYNAMIC FILTERING (Filtrare Complexa)
    // ==========================================
    // Asta e "BOSS LEVEL". Construiesti query-ul dinamic in functie de ce filtre sunt nenule.
    // Ex: Userul filtreaza doar dupa status, dar lasa pretul gol.
//    public List<Item> findAllFiltered(String status, BigDecimal minPrice) {
//        StringBuilder sql = new StringBuilder("SELECT * FROM items WHERE 1=1 ");
//        List<Object> params = new ArrayList<>();
//
//        if (status != null && !status.isEmpty()) {
//            sql.append(" AND status = ?");
//            params.add(status);
//        }
//
//        if (minPrice != null) {
//            sql.append(" AND value >= ?");
//            params.add(minPrice);
//        }
//
//        try (Connection conn = DriverManager.getConnection(url, username, password);
//             PreparedStatement ps = conn.prepareStatement(sql.toString())) {
//
//            // Setam parametrii din lista
//            for (int i = 0; i < params.size(); i++) {
//                Object p = params.get(i);
//                if (p instanceof String) ps.setString(i + 1, (String) p);
//                else if (p instanceof BigDecimal) ps.setBigDecimal(i + 1, (BigDecimal) p);
//                // adauga alte tipuri daca e nevoie
//            }
//
//            try (ResultSet rs = ps.executeQuery()) {
//                List<Item> list = new ArrayList<>();
//                while (rs.next()) {
//                    list.add(extractEntity(rs));
//                }
//                return list;
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }

    // ==========================================
    // 11. SAVE AND RETURN GENERATED ID
    // ==========================================
    // Foarte util cand inserezi ceva si ai nevoie imediat de ID-ul creat de baza de date
//    public Long saveReturnsId(Item item) {
//        String sql = "INSERT INTO items (name, description) VALUES (?, ?)";
//
//        try (Connection connection = DriverManager.getConnection(url, username, password);
//             // Specificam ca vrem cheile generate inapoi
//             PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
//
//            ps.setString(1, item.getName());
//            ps.setString(2, item.getDescription());
//
//            int affectedRows = ps.executeUpdate();
//
//            if (affectedRows == 0) {
//                throw new SQLException("Creating item failed, no rows affected.");
//            }
//
//            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
//                if (generatedKeys.next()) {
//                    return generatedKeys.getLong(1); // Returnam ID-ul
//                } else {
//                    throw new SQLException("Creating item failed, no ID obtained.");
//                }
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }

    // ==========================================
    // 12. BATCH SAVE (Insert Masiv)
    // ==========================================
    // Daca trebuie sa salvezi o lista de 100 de items, nu chema save() de 100 de ori. Foloseste asta.
//    public void saveAll(List<Item> items) {
//        String sql = "INSERT INTO items (name, value) VALUES (?, ?)";
//
//        try (Connection connection = DriverManager.getConnection(url, username, password);
//             PreparedStatement ps = connection.prepareStatement(sql)) {
//
//            // Dezactivam auto-commit pentru viteza si siguranta (tranzactie)
//            connection.setAutoCommit(false);
//
//            for (Item item : items) {
//                ps.setString(1, item.getName());
//                ps.setBigDecimal(2, item.getValue());
//                ps.addBatch(); // Adauga in "galeata"
//            }
//
//            ps.executeBatch(); // Executa tot odata
//            connection.commit(); // Salveaza modificarile
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }

    // ==========================================
    // 13. COUNT (Simpla numarare)
    // ==========================================
    // Util pentru statistici
//    public int countByStatus(String status) {
//        String sql = "SELECT COUNT(*) FROM items WHERE status = ?";
//        try (Connection conn = DriverManager.getConnection(url, username, password);
//             PreparedStatement ps = conn.prepareStatement(sql)) {
//
//            ps.setString(1, status);
//
//            try(ResultSet rs = ps.executeQuery()) {
//                if(rs.next()) {
//                    return rs.getInt(1);
//                }
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        return 0;
//    }
}