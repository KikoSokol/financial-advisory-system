import {html, PolymerElement} from '@polymer/polymer/polymer-element.js';
import '@vaadin/vaadin-form-layout/src/vaadin-form-layout.js';
import '@vaadin/vaadin-text-field/src/vaadin-text-field.js';
import '@vaadin/flow-frontend/vaadin-big-decimal-field.js';
import '@vaadin/vaadin-select/src/vaadin-select.js';
import '@vaadin/vaadin-text-field/src/vaadin-text-area.js';
import '@vaadin/vaadin-date-picker/src/vaadin-date-picker.js';
import '../../client/physical-person-card.js';
import '@vaadin/vaadin-button/src/vaadin-button.js';
import '@vaadin/vaadin-ordered-layout/src/vaadin-horizontal-layout.js';
import '@polymer/iron-icon/iron-icon.js';
import '../../files/file-attachments-viewer.js';
import '../../files/file-attachments-add-view.js';

class LifeInsuranceEditor extends PolymerElement {

    static get template() {
        return html`
<style include="shared-styles">
                :host {
                    display: block;
                    height: 100%;
                }
            </style>
<vaadin-form-layout>
 <vaadin-form-layout>
  <vaadin-text-field label="Ćíslo zmluvy" placeholder="Ćíslo zmluvy" id="contractNumber" required></vaadin-text-field>
  <vaadin-select id="product" label="Produkt" placeholder="Produkt"></vaadin-select>
  <vaadin-date-picker label="Dátum začiatku platnosti" placeholder="Dátum začiatku platnosti" id="dateOfStart" required></vaadin-date-picker>
  <vaadin-big-decimal-field id="payment" required placeholder="Platba" label="Platba"></vaadin-big-decimal-field>
  <vaadin-date-picker label="Dátum výročia" placeholder="Dátum výročia" id="anniversaryDate" required></vaadin-date-picker>
  <vaadin-select id="paymentFrequency" label="Frekvencia platby" required placeholder="Frekvencia platby"></vaadin-select>
  <vaadin-date-picker label="Dátum konca platnosti" placeholder="Dátum konca platnosti" id="dateOfEnd"></vaadin-date-picker>
  <vaadin-text-area label="Poznámka" placeholder="Poznámka" id="note"></vaadin-text-area>
 </vaadin-form-layout>
 <vaadin-form-layout>
  <span colspan="2">Poistník</span>
  <vaadin-button id="addOwner">
   <iron-icon icon="lumo:plus" slot="prefix"></iron-icon>Poistník 
  </vaadin-button>
  <physical-person-card id="ownerInfo" colspan="2"></physical-person-card>
  <span colspan="2">Poistený</span>
  <vaadin-button id="addInsured">
   <iron-icon icon="lumo:plus" slot="prefix"></iron-icon>Poistený 
  </vaadin-button>
  <vaadin-button id="equalsClients">
    = 
  </vaadin-button>
  <physical-person-card id="insuredInfo" colspan="2"></physical-person-card>
 </vaadin-form-layout>
 <vaadin-form-layout>
  <span colspan="2">Prílohy</span>
  <file-attachments-viewer id="attachmentsView" colspan="2"></file-attachments-viewer>
 </vaadin-form-layout>
 <vaadin-form-layout>
  <span colspan="2">Pridaj prílohu</span>
  <file-attachments-add-view id="attachmentsAdd" colspan="2"></file-attachments-add-view>
 </vaadin-form-layout>
</vaadin-form-layout>
<vaadin-horizontal-layout style="justify-content: flex-end;">
 <vaadin-button theme="primary" id="cancel">
   ZRUŠIŤ 
 </vaadin-button>
 <vaadin-button theme="primary success" id="save" style="margin-right: var(--lumo-space-m); margin-left: var(--lumo-space-m);">
  <iron-icon icon="lumo:checkmark"></iron-icon>ULOŽIŤ 
 </vaadin-button>
</vaadin-horizontal-layout>
`;
    }

    static get is() {
        return 'life-insurance-editor';
    }

    static get properties() {
        return {
            // Declare your properties here.
        };
    }
}

customElements.define(LifeInsuranceEditor.is, LifeInsuranceEditor);
