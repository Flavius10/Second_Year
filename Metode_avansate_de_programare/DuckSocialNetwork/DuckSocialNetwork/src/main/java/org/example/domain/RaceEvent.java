package org.example.domain;

public class RaceEvent extends Event{

        private Long id;
        private String name;

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

        @Override
        public String toString() {
            return "RaceEvent{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }

}
