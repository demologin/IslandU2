module com.github.azrielis57.finalisland {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires org.reflections;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.annotation;
    requires jsr305;


    opens com.javarush.island.baksalyar to javafx.fxml;
    exports com.javarush.island.baksalyar;
    exports com.javarush.island.baksalyar.controllers;
    opens com.javarush.island.baksalyar.controllers to javafx.fxml;
    opens com.javarush.island.baksalyar.entity.organizm to com.fasterxml.jackson.databind;
    opens com.javarush.island.baksalyar.entity.organizm.animals.predator to com.fasterxml.jackson.databind;
    exports com.javarush.island.baksalyar.entity.organizm.animals;
    opens com.javarush.island.baksalyar.entity.organizm.plants to com.fasterxml.jackson.databind;

    requires com.fasterxml.jackson.core;

    requires com.fasterxml.jackson.dataformat.yaml;
    requires com.fasterxml.jackson.dataformat.xml;
    requires java.xml;

    opens com.javarush.island.baksalyar.entity.organizm.animals to com.fasterxml.jackson.databind;
    opens com.javarush.island.baksalyar.entity.organizm.animals.herbivores to com.fasterxml.jackson.databind;

}