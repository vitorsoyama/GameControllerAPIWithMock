package project.ApiDio.gameapi.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String gameName;

    @Column(nullable = false)
    private Integer gameYear;

    @Column(nullable = false)
    private String gameProducer;

    @Column(nullable = false)
    private String gameConsole;

    @Column(nullable = false)
    private String situation;

}
