import {html, PolymerElement} from '@polymer/polymer/polymer-element.js';
import '@vaadin/vaadin-date-picker/src/vaadin-date-picker.js';
import '@vaadin/vaadin-text-field/src/vaadin-email-field.js';
import '@vaadin/vaadin-form-layout/src/vaadin-form-layout.js';
import './personal-card-component.js';
import '@vaadin/vaadin-text-field/src/vaadin-text-field.js';
import '@vaadin/vaadin-ordered-layout/src/vaadin-horizontal-layout.js';
import '@vaadin/vaadin-button/src/vaadin-button.js';
import '@polymer/iron-icon/iron-icon.js';
import '@vaadin/vaadin-text-field/src/vaadin-text-area.js';

class SelfEmployedPersonEditor extends PolymerElement {

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
  <span style="width: 100%;" colspan="2">Osobné údaje</span>
  <vaadin-text-field error-message="Please enter a value" required invalid="" label="Meno" id="firstName" clear-button-visible></vaadin-text-field>
  <vaadin-text-field error-message="Please enter a value" required invalid="" label="Priezvisko" id="surname"></vaadin-text-field>
  <vaadin-email-field id="email" required label="Email" invalid></vaadin-email-field>
  <vaadin-text-field error-message="Please enter a value" required invalid="" label="Telefón" id="phone"></vaadin-text-field>
  <vaadin-date-picker label="Dátum narodenia" placeholder="Výber dátum" id="dateOfBirth"></vaadin-date-picker>
 </vaadin-form-layout>
 <vaadin-form-layout>
  <span colspan="2">Údaje OP</span>
  <vaadin-text-field error-message="Please enter a value" required invalid="" label="Rodné číslo" id="personalNumber" colspan="2"></vaadin-text-field>
  <vaadin-text-field error-message="Please enter a value" required invalid="" label="Číslo OP" id="identityCardNumber"></vaadin-text-field>
  <vaadin-text-field error-message="Please enter a value" required invalid="" label="Štátne občiansto" id="citizenship"></vaadin-text-field>
  <vaadin-date-picker label="Dátum platnosti OP" placeholder="Výber dátum" id="dateOfValidityOfIdentityCard"></vaadin-date-picker>
  <vaadin-date-picker label="Dátum vydania OP" placeholder="Výber dátum" id="releaseDateOfIdentityCard"></vaadin-date-picker>
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
  <span colspan="2">Kopia OP</span>
  <personal-card-component id="identityCardCopy" colspan="2"></personal-card-component>
 </vaadin-form-layout>
 <vaadin-form-layout>
  <vaadin-text-field error-message="Please enter a value" required invalid="" label="IČO" id="ico"></vaadin-text-field>
  <vaadin-text-field error-message="Please enter a value" required invalid="" label="Obchodné meno" id="businessName"></vaadin-text-field>
  <vaadin-text-field error-message="Please enter a value" required invalid="" label="Hlavný predmet činnosti" id="businessObject"></vaadin-text-field>
  <vaadin-text-field error-message="Please enter a value" required invalid="" label="Iban" id="iban"></vaadin-text-field>
 </vaadin-form-layout>
 <vaadin-form-layout>
  <vaadin-text-area label="Poznámka" id="note"></vaadin-text-area>
 </vaadin-form-layout>
</vaadin-form-layout>
<vaadin-horizontal-layout style="justify-content: flex-end;">
 <vaadin-button theme="primary error" id="delete" style="margin-right: var(--lumo-space-m);">
  VYMAZAŤ
 </vaadin-button>
 <vaadin-button theme="primary" id="cancel">
   ZRUŠIŤ 
 </vaadin-button>
 <vaadin-button theme="primary success" id="save" style="flex-grow: 0; flex-shrink: 1; margin-left: var(--lumo-space-m); margin-right: var(--lumo-space-m);">
  <iron-icon icon="lumo:checkmark"></iron-icon>ULOŽIŤ 
 </vaadin-button>
</vaadin-horizontal-layout>
`;
    }

    static get is() {
        return 'self-employed-person-editor';
    }

    static get properties() {
        return {
            // Declare your properties here.
        };
    }
}

customElements.define(SelfEmployedPersonEditor.is, SelfEmployedPersonEditor);
