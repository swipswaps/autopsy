/*
 * Autopsy Forensic Browser
 *
 * Copyright 2018 Basis Technology Corp.
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
package org.sleuthkit.autopsy.commonfilesearch;

import java.awt.event.ActionEvent;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import javax.swing.SwingWorker;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.openide.util.actions.CallableSystemAction;
import org.sleuthkit.autopsy.casemodule.Case;
import org.sleuthkit.autopsy.coreutils.Logger;

/**
 * Encapsulates a menu action which triggers the common files search dialog.
 */
final public class CommonAttributeSearchAction extends CallableSystemAction {

    private static final Logger LOGGER = Logger.getLogger(CommonAttributeSearchAction.class.getName());

    private static CommonAttributeSearchAction instance = null;
    private static final long serialVersionUID = 1L;

    /**
     * Get the default CommonAttributeSearchAction.
     *
     * @return the default instance of this action
     */
    public static synchronized CommonAttributeSearchAction getDefault() {
        if (instance == null) {
            instance = new CommonAttributeSearchAction();
        }
        return instance;
    }

    /**
     * Create a CommonAttributeSearchAction for opening the common attribute
     * search dialog
     */
    private CommonAttributeSearchAction() {
        super();
        this.setEnabled(false);
    }

    @Override
    public boolean isEnabled() {
        return super.isEnabled() && Case.isCaseOpen();
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        createAndShowPanel();
    }

    @Override
    public void performAction() {
        createAndShowPanel();
    }

    /**
     * Create the commonAttributePanel and display it.
     */
    @NbBundle.Messages({
        "CommonAttributeSearchAction.openPanel.noCaseOpen=Unable to run Common Property Search - no case is open.",
        "CommonAttributeSearchAction.openPanel.notEnoughData=Unable to run Common Property Search - there are not enough data sources available in the current case and/or the Central Repository."})
    private void createAndShowPanel() {
        new SwingWorker<Boolean, Void>() {

            String reason = "";
            
            @Override
            protected Boolean doInBackground() throws Exception {
                // Test whether we should open the common files panel
                if(! Case.isCaseOpen()) {
                    reason = Bundle.CommonAttributeSearchAction_openPanel_noCaseOpen();
                    return false;
                }
                
                if (Case.getCurrentCase().getDataSources().size() > 1) {
                    // There are enough data sources to run the intra case seach
                    return true;
                }
                
                if( ! CommonAttributePanel.isEamDbAvailableForIntercaseSearch()) {
                    reason = Bundle.CommonAttributeSearchAction_openPanel_notEnoughData();
                    return false;
                }
                return true;
            }

            @Override
            protected void done() {
                super.done();
                try {
                    boolean openPanel = get();
                    if(openPanel) {
                        CommonAttributePanel commonAttributePanel = new CommonAttributePanel();
                        //In order to update errors the CommonAttributePanel needs to observe its sub panels
                        commonAttributePanel.observeSubPanels();
                        commonAttributePanel.setVisible(true);
                    } else {
                        NotifyDescriptor descriptor = new NotifyDescriptor.Message(reason, NotifyDescriptor.INFORMATION_MESSAGE);
                        DialogDisplayer.getDefault().notify(descriptor);
                    }
                } catch (InterruptedException | ExecutionException ex) {
                    LOGGER.log(Level.SEVERE, "Unexpected exception while opening Common Properties Search", ex); //NON-NLS
                }
            }
        }.execute();
    }

    @NbBundle.Messages({
        "CommonAttributeSearchAction.getName.text=Common Property Search"})
    @Override
    public String getName() {
        return Bundle.CommonAttributeSearchAction_getName_text();
    }

    @Override
    public HelpCtx getHelpCtx() {
        return HelpCtx.DEFAULT_HELP;
    }
}