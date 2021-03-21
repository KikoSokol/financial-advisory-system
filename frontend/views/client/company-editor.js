import {html, PolymerElement} from '@polymer/polymer/polymer-element.js';
import '@vaadin/vaadin-form-layout/src/vaadin-form-layout.js';
import '@vaadin/vaadin-ordered-layout/src/vaadin-horizontal-layout.js';
import '@vaadin/vaadin-button/src/vaadin-button.js';
import '@polymer/iron-icon/iron-icon.js';
import '@vaadin/vaadin-grid/src/vaadin-grid.js';
import './search-client-view.js';
import '@vaadin/vaadin-text-field/src/vaadin-text-area.js';
import '@vaadin/vaadin-text-field/src/vaadin-text-field.js';

class CompanyEditor extends PolymerElement {

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
  <span colspan="2">Hlavné údaje</span>
  <vaadin-text-field error-message="Please enter a value" required invalid="" label="Obchodné meno" id="businessName" colspan="1"></vaadin-text-field>
  <vaadin-text-field error-message="Please enter a value" required invalid="" label="IČO" id="ico"></vaadin-text-field>
  <vaadin-text-field error-message="Please enter a value" required invalid="" label="DIČ" id="dic"></vaadin-text-field>
  <vaadin-text-field error-message="Please enter a value" required invalid="" label="DIČ DPH" id="dicDph"></vaadin-text-field>
  <vaadin-text-field error-message="Please enter a value" required invalid="" label="Hlavný predmet činnosti" id="businessObject"></vaadin-text-field>
  <vaadin-text-area label="Poznámka" id="note"></vaadin-text-area>
 </vaadin-form-layout>
 <vaadin-form-layout>
  <span colspan="2">Adresa</span>
  <vaadin-text-field error-message="Please enter a value" required invalid="" label="Ulica" id="street" colspan="2"></vaadin-text-field>
  <vaadin-text-field error-message="Please enter a value" required invalid="" label="Číslo domu" id="numberOfHouse"></vaadin-text-field>
  <vaadin-text-field error-message="Please enter a value" required invalid="" label="Mesto" id="city"></vaadin-text-field>
  <vaadin-text-field error-message="Please enter a value" required invalid="" label="PSČ" id="postalCode"></vaadin-text-field>
  <vaadin-text-field error-message="Please enter a value" required invalid="" label="Štát" id="state"></vaadin-text-field>
 </vaadin-form-layout>
 <vaadin-form-layout>
  <span colspan="1">Konatelia 
   <vaadin-button theme="icon" aria-label="Add new" id="addManager">
    <iron-icon icon="lumo:plus"></iron-icon>
   </vaadin-button></span>
  <vaadin-grid colspan="2" id="managerTable"></vaadin-grid>
 </vaadin-form-layout>
 <vaadin-form-layout>
  <search-client-view id="seachManager" colspan="2"></search-client-view>
 </vaadin-form-layout>
</vaadin-form-layout>
<vaadin-horizontal-layout style="justify-content: flex-end;">
 <vaadin-button theme="primary" id="cancel">
   ZRUŠIŤ 
 </vaadin-button>
 <vaadin-button theme="primary success" style="margin-right: var(--lumo-space-m); margin-left: var(--lumo-space-m);" id="save">
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
