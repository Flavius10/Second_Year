package org.example.domain;

import org.example.domain.ducks.Duck;
import org.example.domain.ducks.Lane;
import org.example.domain.ducks.SwimmingDuck;

import java.util.List;

public class RaceEvent extends Event{

        private Long id;
        private String name;
        private List<Lane> lanes;

        public RaceEvent(Long id, String name) {
            this.id = id;
            this.name = name;
        }

        public Long getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<Lane> getLanes() {
            return lanes;
        }

        public void setLanes(List<Lane> lanes) {
            this.lanes = lanes;
        }

        @Override
        public String toString() {
            return "RaceEvent{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }

}
