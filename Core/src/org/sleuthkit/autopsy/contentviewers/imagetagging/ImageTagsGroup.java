/*
 * Autopsy Forensic Browser
 *
 * Copyright 2019 Basis Technology Corp.
 * Contact: carrier <at> sleuthkit <dot> org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.sleuthkit.autopsy.contentviewers.imagetagging;

import com.sun.javafx.event.EventDispatchChainImpl;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import javafx.event.Event;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

/**
 * Manages the focus and z-ordering of ImageTags. Only one image tag may be
 * selected at a time. Image tags show their 8 edit "handles" upon selection
 * (see ImageTag class for more details) and get the highest z-ordering to make
 * editing easier. This class is responsible for setting and dropping focus as
 * the user navigates from tag to tag. The ImageTag is treated as a logical
 * unit, however it's underlying representation consists of the physical
 * rectangle and the 8 edit handles. JavaFX will report selection on the Node
 * level (so either the Rectangle, or a singe edit handle), which makes keeping
 * the entire image tag in focus a non-trivial problem.
 */
public final class ImageTagsGroup extends Group {

    private final EventDispatchChainImpl NO_OP_CHAIN = new EventDispatchChainImpl();
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    private volatile ImageTag currentFocus;

    public ImageTagsGroup(Node backDrop) {

        //Reset focus of current selection if the back drop has focus.
        backDrop.setOnMousePressed((mouseEvent) -> {
            if (currentFocus != null) {
                currentFocus.getEventDispatcher().dispatchEvent(
                        new Event(ImageTagControls.NOT_FOCUSED), NO_OP_CHAIN);
                currentFocus = null;
            }

            this.pcs.firePropertyChange(new PropertyChangeEvent(this,
                    ImageTagControls.NOT_FOCUSED.getName(), currentFocus, null));
        });

        //Set the focus of selected tag
        this.addEventFilter(MouseEvent.MOUSE_PRESSED, (MouseEvent e) -> {
            if (!e.isPrimaryButtonDown()) {
                return;
            }

            //Pull out the logical image tag that this node is associated with
            Node topLevelChild = e.getPickResult().getIntersectedNode();
            while (!this.getChildren().contains(topLevelChild)) {
                topLevelChild = topLevelChild.getParent();
            }

            requestFocus((ImageTag) topLevelChild);
        });
    }

    /**
     * Subscribe to focus change events on Image tags.
     *
     * @param fcl PCL to be notified which Image tag has been selected.
     */
    public void addFocusChangeListener(PropertyChangeListener fcl) {
        this.pcs.addPropertyChangeListener(fcl);
    }

    /**
     * Get the image tag that current has focus.
     * 
     * @return ImageTag instance or null if no tag is in focus.
     */
    public ImageTag getFocus() {
        return currentFocus;
    }
    
    /**
     * Clears the current focus
     */
    public void clearFocus() {
        if(currentFocus != null) {
            resetFocus(currentFocus);
            currentFocus = null;
        }
    }

    /**
     * Notifies the logical image tag that it is no longer in focus.
     * 
     * @param n 
     */
    private void resetFocus(ImageTag n) {
        n.getEventDispatcher().dispatchEvent(new Event(ImageTagControls.NOT_FOCUSED), NO_OP_CHAIN);
        this.pcs.firePropertyChange(new PropertyChangeEvent(this, ImageTagControls.NOT_FOCUSED.getName(), n, null));
    }

    /**
     * Notifies the logical image that it is in focus.
     * 
     * @param n 
     */
    private void requestFocus(ImageTag n) {
        if (currentFocus == n) {
            return;
        } else if (currentFocus != null && currentFocus != n) {
            resetFocus(currentFocus);
        }

        n.getEventDispatcher().dispatchEvent(new Event(ImageTagControls.FOCUSED), NO_OP_CHAIN);
        this.pcs.firePropertyChange(new PropertyChangeEvent(this, ImageTagControls.FOCUSED.getName(), currentFocus, n));

        currentFocus = n;
        n.toFront();
    }
}
