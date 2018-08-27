/*
 * 
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

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.swing.ComboBoxModel;
import org.openide.util.Exceptions;
import org.sleuthkit.autopsy.centralrepository.datamodel.CorrelationAttributeInstance;
import org.sleuthkit.autopsy.centralrepository.datamodel.EamDbException;

/**
 * UI controls for Common Files Search scenario where the user intends to find
 * common files between cases in addition to the present case.
 */
public class InterCasePanel extends javax.swing.JPanel {
    
    private static final long serialVersionUID = 1L;
    
    static final int NO_CASE_SELECTED = -1;
    
    private ComboBoxModel<String> casesList = new DataSourceComboBoxModel();
    
    private final Map<Integer, String> caseMap;
        
    //True if we are looking in any or all cases,
    //  false if we must find matches in a given case plus the current case
    private boolean anyCase;
    
    private Map<String, CorrelationAttributeInstance.Type> correlationTypeFilters;
    
    /**
     * Creates new form InterCasePanel
     */
    public InterCasePanel() {
        initComponents();
        this.caseMap = new HashMap<>();
        this.anyCase = true;

        
    }

    private void specificCaseSelected(boolean selected) {
        this.specificCentralRepoCaseRadio.setEnabled(selected);
        if (this.specificCentralRepoCaseRadio.isEnabled()) {
            this.caseComboBox.setEnabled(true);
            this.caseComboBox.setSelectedIndex(0);
        }
    }
    
    void setupCorrelationTypeFilter() {
        this.correlationTypeFilters = new HashMap<>();
        try {
            List<CorrelationAttributeInstance.Type> types = CorrelationAttributeInstance.getDefaultCorrelationTypes();
            for (CorrelationAttributeInstance.Type type : types) {
                correlationTypeFilters.put(type.getDisplayName(), type);
                this.correlationTypeComboBox.addItem(type.getDisplayName());
            }
        } catch (EamDbException ex) {
            Exceptions.printStackTrace(ex);
        }
        this.correlationTypeComboBox.setSelectedIndex(0);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup = new javax.swing.ButtonGroup();
        anyCentralRepoCaseRadio = new javax.swing.JRadioButton();
        specificCentralRepoCaseRadio = new javax.swing.JRadioButton();
        caseComboBox = new javax.swing.JComboBox<>();
        comboBoxLabel = new javax.swing.JLabel();
        correlationTypeComboBox = new javax.swing.JComboBox<>();

        buttonGroup.add(anyCentralRepoCaseRadio);
        anyCentralRepoCaseRadio.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(anyCentralRepoCaseRadio, org.openide.util.NbBundle.getMessage(InterCasePanel.class, "InterCasePanel.anyCentralRepoCaseRadio.text")); // NOI18N
        anyCentralRepoCaseRadio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                anyCentralRepoCaseRadioActionPerformed(evt);
            }
        });

        buttonGroup.add(specificCentralRepoCaseRadio);
        org.openide.awt.Mnemonics.setLocalizedText(specificCentralRepoCaseRadio, org.openide.util.NbBundle.getMessage(InterCasePanel.class, "InterCasePanel.specificCentralRepoCaseRadio.text")); // NOI18N
        specificCentralRepoCaseRadio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                specificCentralRepoCaseRadioActionPerformed(evt);
            }
        });

        caseComboBox.setModel(casesList);
        caseComboBox.setEnabled(false);

        org.openide.awt.Mnemonics.setLocalizedText(comboBoxLabel, org.openide.util.NbBundle.getMessage(InterCasePanel.class, "InterCasePanel.comboBoxLabel.text")); // NOI18N

        correlationTypeComboBox.setSelectedItem(null);
        correlationTypeComboBox.setToolTipText(org.openide.util.NbBundle.getMessage(InterCasePanel.class, "InterCasePanel.correlationTypeComboBox.toolTipText")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(anyCentralRepoCaseRadio)
                    .addComponent(specificCentralRepoCaseRadio)
                    .addComponent(comboBoxLabel)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(correlationTypeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(caseComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(anyCentralRepoCaseRadio)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(specificCentralRepoCaseRadio)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(caseComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(comboBoxLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(correlationTypeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void specificCentralRepoCaseRadioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_specificCentralRepoCaseRadioActionPerformed
        this.caseComboBox.setEnabled(true);
        if(this.caseComboBox.isEnabled() && this.caseComboBox.getSelectedItem() == null){
            this.caseComboBox.setSelectedIndex(0);
        }
        this.anyCase = false;
    }//GEN-LAST:event_specificCentralRepoCaseRadioActionPerformed

    private void anyCentralRepoCaseRadioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_anyCentralRepoCaseRadioActionPerformed
        this.caseComboBox.setEnabled(false);
        this.anyCase = true;
    }//GEN-LAST:event_anyCentralRepoCaseRadioActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton anyCentralRepoCaseRadio;
    private javax.swing.ButtonGroup buttonGroup;
    private javax.swing.JComboBox<String> caseComboBox;
    private javax.swing.JLabel comboBoxLabel;
    private javax.swing.JComboBox<String> correlationTypeComboBox;
    private javax.swing.JRadioButton specificCentralRepoCaseRadio;
    // End of variables declaration//GEN-END:variables

    Map<Integer, String> getCaseMap() {
        return Collections.unmodifiableMap(this.caseMap);
    }

    void setCaseList(DataSourceComboBoxModel dataSourceComboBoxModel) {
        this.casesList = dataSourceComboBoxModel;
        this.caseComboBox.setModel(dataSourceComboBoxModel);
    }

    void rigForMultipleCases(boolean multipleCases) {
        this.anyCentralRepoCaseRadio.setEnabled(multipleCases);
        this.anyCentralRepoCaseRadio.setEnabled(multipleCases);
        
        if(!multipleCases){
            this.specificCentralRepoCaseRadio.setSelected(true);
            this.specificCaseSelected(true);
        }
    }

    void setCaseMap(Map<Integer, String> caseMap) {
        this.caseMap.clear();
        this.caseMap.putAll(caseMap);
    }

    boolean centralRepoHasMultipleCases() {
        return this.caseMap.size() >= 2;
    }
    
    Integer getSelectedCaseId(){
        if(this.anyCase){
            return InterCasePanel.NO_CASE_SELECTED;
        }
        
        for(Entry<Integer, String> entry : this.caseMap.entrySet()){
            if(entry.getValue().equals(this.caseComboBox.getSelectedItem())){
                return entry.getKey();
            }
        }
        
        return InterCasePanel.NO_CASE_SELECTED;
    }
    
    CorrelationAttributeInstance.Type getSelectedCorrelationType() {
        return correlationTypeFilters.get(this.correlationTypeComboBox.getSelectedItem().toString());
    }
}
