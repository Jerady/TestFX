package org.loadui.testfx;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBoxBuilder;

import org.junit.Test;
import org.loadui.testfx.utils.FXTestUtils;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.matchers.JUnitMatchers.hasItems;
import static org.loadui.testfx.Assertions.verifyThat;
import static org.loadui.testfx.controls.ListViews.containsRow;
import static org.loadui.testfx.controls.ListViews.numberOfRowsIn;

public class DragDropTest extends GuiTest {

	private final ListView<String> list1 = new ListView<>();
	private final ListView<String> list2 = new ListView<>();

	/**
	 * A typical event handler to start a drag-drop operation.
	 */
	private final EventHandler<MouseEvent> onDragDetected = new EventHandler<MouseEvent>() {
		public void handle(MouseEvent event) {

			Dragboard dragboard = ((Node) event.getSource()).startDragAndDrop(TransferMode.MOVE);

			ClipboardContent content = new ClipboardContent();
			content.putString("");
			dragboard.setContent(content);

			event.consume();
		}
	};

	@Override
	protected Parent getRootNode() {
		list1.setOnDragDetected( onDragDetected );

		return HBoxBuilder.create().children(list1, list2).build();
	}

	@SuppressWarnings("unchecked")
	@Test
	public void shouldMoveElements() throws Exception {

		FXTestUtils.invokeAndWait(new Runnable() {
			public void run() {
				list1.getItems().addAll("A", "B", "C");
				list2.getItems().addAll("X", "Y", "Z");
			}
		}, 1000);

		verifyThat(numberOfRowsIn(list1), is(3));
		verifyThat(numberOfRowsIn(list2), is(3));

		drag("A").to( "X" );
	}
}