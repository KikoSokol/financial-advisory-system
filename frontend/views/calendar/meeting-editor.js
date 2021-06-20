import {html, PolymerElement} from '@polymer/polymer/polymer-element.js';
import '@vaadin/vaadin-ordered-layout/src/vaadin-horizontal-layout.js';
import '@vaadin/vaadin-text-field/src/vaadin-text-area.js';
import '@vaadin/vaadin-button/src/vaadin-button.js';
import '@polymer/iron-icon/iron-icon.js';
import '../client/physical-person-card.js';
import '@vaadin/vaadin-text-field/src/vaadin-text-field.js';
import '@vaadin/vaadin-form-layout/src/vaadin-form-layout.js';

class MeetingEditor extends PolymerElement {

    static get template() {
        return html`
<style include="shared-styles">
                :host {
                    display: block;
                    height: 100%;
                }
            </style>
<vaadin-form-layout>
 <vaadin-horizontal-layout id="dateTimePlace" style="flex-wrap: wrap; align-content: center;" colspan="2"></vaadin-horizontal-layout>
 <vaadin-text-area label="Poznámka" placeholder="Poznámka" id="description" colspan="2"></vaadin-text-area>
 <label colspan="2">Klient</label>
 <vaadin-button id="addClient">
  <iron-icon icon="lumo:plus" slot="prefix"></iron-icon>Klient 
 </vaadin-button>
 <vaadin-button id="useClientAddress">
   Použi adresu klienta 
 </vaadin-button>
 <physical-person-card id="clientInfo" colspan="2"></physical-person-card>
 <label colspan="2">Adresa stretnutia</label>
 <vaadin-text-field label="Ulica" placeholder="Ulica" id="street"></vaadin-text-field>
 <vaadin-text-field label="Číslo domu" placeholder="Číslo domu" id="numberOfHouse"></vaadin-text-field>
 <vaadin-text-field label="PSČ" placeholder="PSČ" id="postalCode"></vaadin-text-field>
 <vaadin-text-field label="Mesto" placeholder="Mesto" id="city"></vaadin-text-field>
 <vaadin-text-field label="Štát" placeholder="Štát" id="state"></vaadin-text-field>
</vaadin-form-layout>
<vaadin-horizontal-layout style="width: 100%; justify-content: flex-end; flex-wrap: wrap; align-content: center;">
 <vaadin-button theme="primary error" id="delete" style="margin-right: var(--lumo-space-m);">
   VYMAZAŤ 
 </vaadin-button>
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
        return 'meeting-editor';
    }

    static get properties() {
        return {
            // Declare your properties here.
        };
    }
}

customElements.define(MeetingEditor.is, MeetingEditor);
