import {html, PolymerElement} from '@polymer/polymer/polymer-element.js';
import '@vaadin/vaadin-text-field/src/vaadin-text-field.js';
import '@vaadin/vaadin-form-layout/src/vaadin-form-layout.js';
import '@vaadin/vaadin-ordered-layout/src/vaadin-horizontal-layout.js';
import '@vaadin/vaadin-button/src/vaadin-button.js';
import '@polymer/iron-icon/iron-icon.js';

class CompanyEditor extends PolymerElement {

    static get template() {
        return html`
<style include="shared-styles">
                :host {
                    display: block;
                    height: 100%;
                }
            </style>
<vaadin-form-layout colspan="">
 <vaadin-text-field label="Názov spoločnosti" placeholder="Názov spoločnosti" id="name" required></vaadin-text-field>
 <vaadin-text-field label="IČO" placeholder="IČO" id="ico" required></vaadin-text-field>
 <span colspan="2">Adresa spoločnosti</span>
 <vaadin-text-field label="Ulica" placeholder="Ulica" id="street" required colspan="2"></vaadin-text-field>
 <vaadin-text-field label="Číslo domu" placeholder="Číslo domu" id="numberOfHouse" required></vaadin-text-field>
 <vaadin-text-field label="PSČ" placeholder="PSČ" id="postalCode" required></vaadin-text-field>
 <vaadin-text-field label="Mesto" placeholder="Mesto" id="city" required></vaadin-text-field>
 <vaadin-text-field label="Štát" placeholder="Štát" id="state" required></vaadin-text-field>
</vaadin-form-layout>
<vaadin-horizontal-layout style="justify-content: flex-end; width: 100%;">
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
        return 'company-editor';
    }

    static get properties() {
        return {
            // Declare your properties here.
        };
    }
}

customElements.define(CompanyEditor.is, CompanyEditor);
