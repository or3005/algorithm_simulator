package algorithm;

import java.util.Arrays;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Represents the statistics view of the algorithm.
 */
public class Statistics extends Application {

	// Data matrix for table
	private String[][] dataMatrix = {
			{ "Number of nodes", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Average" },
			{ "3", "2", "6", "4", "5", "4", "4", "3", "4", "2", "3", "3.7" },
			{ "5", "6", "6", "3", "8", "5", "4", "5", "5", "3", "6", "5.1" },
			{ "10", "10", "13", "14", "13", "10", "7", "13", "17", "10", "8", "11.5" },
			{ "12", "11", "17", "15", "17", "12", "10", "14", "14", "12", "14", "13.6" },
			{ "15", "21", "11", "13", "21", "18", "13", "18", "17", "17", "11", "16" },
			{ "18", "24", "20", "18", "21", "19", "23", "22", "25", "21", "18", "21.1" },
			{ "20", "22", "24", "29", "27", "20", "18", "21", "19", "26", "24", "23" } };

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Statistics");

		// Define the axes for the line chart
		NumberAxis xAxis = new NumberAxis(0, 25, 5);
		xAxis.setLabel("Number of Nodes");

		NumberAxis yAxis = new NumberAxis(0, 25, 1);
		yAxis.setLabel("Average Turns");

		// Create the line chart
		LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
		lineChart.setTitle("Algorithm Statistics");

		// Create series for the line chart
		XYChart.Series<Number, Number> series = new XYChart.Series<>();
		series.setName("Average Turns to Reach Perfect State");

		// Add data points to the series
		series.getData().add(new XYChart.Data<>(3, 3.7));
		series.getData().add(new XYChart.Data<>(5, 5.1));
		series.getData().add(new XYChart.Data<>(10, 11.5));
		series.getData().add(new XYChart.Data<>(12, 13.6));
		series.getData().add(new XYChart.Data<>(15, 16));
		series.getData().add(new XYChart.Data<>(18, 21.1));
		series.getData().add(new XYChart.Data<>(20, 23));

		// Add the series to the chart
		lineChart.getData().add(series);

		// Create table
		ObservableList<String[]> data = FXCollections.observableArrayList();
		data.addAll(Arrays.asList(dataMatrix));
		data.remove(0); // Remove titles from data

		TableView<String[]> table = new TableView<>();
		for (int i = 0; i < dataMatrix[0].length; i++) {
			TableColumn<String[], String> tc = new TableColumn<>(dataMatrix[0][i]);
			final int colNo = i;
			tc.setCellValueFactory(p -> new SimpleStringProperty((p.getValue()[colNo])));
			tc.setPrefWidth(150); // Set column width
			tc.setMinWidth(100); // Set minimum column width

			// Set cell factory to customize cell appearance
			tc.setCellFactory(column -> {
				TableCell<String[], String> cell = new TableCell<String[], String>() {
					@Override
					protected void updateItem(String item, boolean empty) {
						super.updateItem(item, empty);
						setText(empty ? null : item);
						setGraphic(null);
					}
				};

				// Set preferred height for the cell
				cell.setPrefHeight(50);
				return cell;
			});

			table.getColumns().add(tc);
		}

		table.setItems(data);

		// Create back button
		Button backButton = new Button("Back");
		backButton.setOnAction(e -> {
			HelloApplication helloApplication = new HelloApplication();
			try {
				helloApplication.start(primaryStage);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		});
		backButton.setStyle(
				"-fx-background-color: #008CBA; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 6px 10px; -fx-border-radius: 4px;");

		// Add the table, chart, and back button to the layout
		VBox vbox = new VBox(table, lineChart, backButton);

		// Create the scene and add the layout to it
		Scene scene = new Scene(vbox, 1800, 1000);
		primaryStage.setScene(scene);

		// Show the stage
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
