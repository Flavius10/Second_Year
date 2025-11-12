    package org.example.domain.ducks;

    import org.example.domain.TypeDuck;
    import org.example.domain.User;
    import org.example.domain.ducks.card.Card;

    /**
     * The type Duck.
     */
    public abstract class Duck extends User {

        private TypeDuck tip;
        private double viteza;
        private double rezistenta;
        private Card card;

        /**
         * Instantiates a new Duck.
         *
         * @param id         the id
         * @param name       the name
         * @param email      the email
         * @param password   the password
         * @param tip        the tip
         * @param viteza     the viteza
         * @param rezistenta the rezistenta
         * @param card       the card
         */
        public Duck(Long id, String name, String email, String password,
                    TypeDuck tip, double viteza, double rezistenta, Card card){
            super(id, name, email, password);
            this.tip = tip;
            this.viteza = viteza;
            this.rezistenta = rezistenta;
            this.card = card;
        }

        /**
         * Getters @return  the tip
         */
        public TypeDuck getTip() {
            return this.tip;
        }

        /**
         * Gets viteza.
         *
         * @return the viteza
         */
        public double getViteza() {
            return this.viteza;
        }

        /**
         * Gets rezistenta.
         *
         * @return the rezistenta
         */
        public double getRezistenta() {
            return this.rezistenta;
        }

        /**
         * Gets card.
         *
         * @return the card
         */
        public Card getCard() {
            return this.card;
        }

        /**
         * Setters @param tip the tip
         */
        public void setTip(TypeDuck tip) {
            this.tip = tip;
        }

        /**
         * Sets viteza.
         *
         * @param viteza the viteza
         */
        public void setViteza(double viteza) {
            this.viteza = viteza;
        }

        /**
         * Sets rezistenta.
         *
         * @param rezistenta the rezistenta
         */
        public void setRezistenta(double rezistenta) {
            this.rezistenta = rezistenta;
        }

        /**
         * Sets card.
         *
         * @param card the card
         */
        public void setCard(Card card) {
            this.card = card;
        }

        @Override
        public String toString() {
            return "Duck{" +
                    "tip=" + tip +
                    ", viteza=" + viteza +
                    ", rezistenta=" + rezistenta +
                    ", card=" + card +
                    '}';
        }

        @Override
        public boolean equals(Object obj){
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            Duck other = (Duck) obj;
            return other.getId().equals(this.getId());
        }

        @Override
        public int hashCode() {
            return this.getId().hashCode();
        }

    }
