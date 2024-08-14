package com.pulsar.habitica.entity.task;

public class Habit extends TaskBase {

    private Integer goodSeries;
    private Integer badSeries;

    private Habit(HabitBuilder builder) {
        super(builder);
        this.goodSeries = builder.goodSeries;
        this.badSeries = builder.badSeries;
    }

    public Integer getGoodSeries() {
        return goodSeries;
    }

    public void setGoodSeries(Integer goodSeries) {
        this.goodSeries = goodSeries;
    }

    public Integer getBadSeries() {
        return badSeries;
    }

    public void setBadSeries(Integer badSeries) {
        this.badSeries = badSeries;
    }

    @Override
    public String toString() {
        return "Habit{" +
                "goodSeries=" + goodSeries +
                ", badSeries=" + badSeries +
                ", id=" + id +
                ", heading='" + heading + '\'' +
                ", description='" + description + '\'' +
                ", complexity=" + complexity +
                ", userId=" + userId +
                '}';
    }

    public static class HabitBuilder extends TaskBase.Builder<HabitBuilder> {

        private Integer goodSeries;
        private Integer badSeries;

        private HabitBuilder() {}

        public HabitBuilder goodSeries(Integer goodSeries) {
            this.goodSeries = goodSeries;
            return self();
        }

        public HabitBuilder badSeries(Integer badSeries) {
            this.badSeries = badSeries;
            return self();
        }

        @Override
        protected HabitBuilder self() {
            return this;
        }

        @Override
        public Habit build() {
            return new Habit(this);
        }
    }

    public static HabitBuilder builder() {
        return new HabitBuilder();
    }
}