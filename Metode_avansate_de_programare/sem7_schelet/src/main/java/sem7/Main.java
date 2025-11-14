package sem7;

import sem7.config.Config;
import sem7.domain.Movie;
import sem7.repository.MovieDBRepository;
import sem7.repository.MovieRepository;
import sem7.service.MovieService;
import sem7.util.paging.Page;
import sem7.util.paging.Pageable;
import sem7.validator.MovieValidator;

import javax.crypto.spec.PSource;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        // 1. Config DB
        Properties props = Config.getProperties();
        String dbUrl = props.getProperty("db.url");
        String dbUsername = props.getProperty("db.username");
        String dbPassword = props.getProperty("db.password");

        // 2. Repository + Service
        MovieRepository movieRepository = new MovieDBRepository(dbUrl, dbUsername, dbPassword);
        MovieService movieService = new MovieService(movieRepository, new MovieValidator());

        // 3. Paginare simplă (2 filme/pagină)
        int pageSize = 2;
        int currentPage = 0;

        Scanner scanner = new Scanner(System.in);

        while(true){
            System.out.println("Page " + (currentPage + 1) + ":");
            Pageable pageable = new Pageable(currentPage, pageSize);
            Page<Movie> moviePage = movieService.findAllOnPage(pageable);
            for (Movie movie : moviePage.getElementsOnPage()) {
                System.out.println(movie);
            }

            System.out.println("N. Next page");
            System.out.println("P. Previous page");
            System.out.println("Q. exit");

            String input = scanner.nextLine();
            if(input.equals("Q")){
                break;
            }else if(input.equals("N")){
                currentPage++;
            }else if(input.equals("P")){
                currentPage--;
            }else{
                System.out.println("Invalid input");
            }
        }

        System.out.println("Final aplicatie");

        
    }
}
