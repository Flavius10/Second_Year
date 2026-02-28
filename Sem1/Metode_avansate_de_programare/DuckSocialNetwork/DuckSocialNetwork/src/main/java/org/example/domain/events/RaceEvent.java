package org.example.domain.events;

import org.example.domain.Entity;
import org.example.domain.User;
import org.example.domain.ducks.Lane;
import org.example.domain.ducks.SwimmingDuck;
import org.example.utils.Constants;

import java.util.List;

public class RaceEvent extends Event {

        private String name;
        private List<Lane> lanes;
        private String message;
        private List<SwimmingDuck> ducks_final;

        public RaceEvent(Long id, String name) {
            this.id = id;
            this.name = name;
        }

        public String getName() {
            return name;
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

        public String getMessage() {
            return this.message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public List<SwimmingDuck> getDucks_final() {
            return ducks_final;
        }

        public void setDucks_final(List<SwimmingDuck> ducks_final) {
            this.ducks_final = ducks_final;
        }

        @Override
        public String toString() {
            return "RaceEvent{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }

        public void startRace(List<SwimmingDuck> ducks, List<Lane> lanes){
            DuckSelector selector = new DuckSelector();
            RaceEvaluator raceEvaluator = new RaceEvaluator(0.0);
            FeasibilityChecker checker = new FeasibilityChecker(raceEvaluator);
            RaceService raceService = new RaceService(checker, selector, ducks, lanes);

            raceService.runRace(Constants.CONSTANT_TIME);
            List<SwimmingDuck> swimmingDucks = raceService.getRaceResult();
            this.ducks_final = swimmingDucks;

            String result = "";
            for (SwimmingDuck duck : swimmingDucks) {
                result += duck.getUsername() + " ";
            }

            this.message = result;

            System.out.println(result);
        }

}
