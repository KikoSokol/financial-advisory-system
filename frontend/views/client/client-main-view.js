import {html, PolymerElement} from '@polymer/polymer/polymer-element.js';
import '@vaadin/vaadin-ordered-layout/src/vaadin-vertical-layout.js';
import '@vaadin/vaadin-ordered-layout/src/vaadin-horizontal-layout.js';
import '@vaadin/vaadin-button/src/vaadin-button.js';
import '@vaadin/vaadin-grid/src/vaadin-grid.js';
import '@vaadin/vaadin-grid/src/vaadin-grid-filter-column.js';

class ClientMainView extends PolymerElement {

    static get template() {
        return html`
<style include="shared-styles">
                :host {
                    display: block;
                    height: 100%;
                }
            </style>
<vaadin-vertical-layout style="width: 100%; height: 100%;">
 <vaadin-horizontal-layout theme="spacing" style="width: 100%; align-self: center; justify-content: center;">
  <label id="label" style="align-self: center; flex-grow: 0; flex-shrink: 1;">Pridaj:</label>
  <vaadin-button theme="primary" id="addPhysicalPersonButton" style="flex-grow: 0; flex-shrink: 0;">
   Fyzická osoba
  </vaadin-button>
  <vaadin-button theme="primary" id="addSelfEmployedPersonButton" style="flex-grow: 0; flex-shrink: 0;">
   Živnostik
  </vaadin-button>
  <vaadin-button theme="primary" id="addCompanyButton" style="flex-grow: 0; flex-shrink: 0;">
   Spoločnosť
  </vaadin-button>
 </vaadin-horizontal-layout>
 <vaadin-grid id="clientTable">
  <vaadin-grid-filter-column id="nameColumn" auto-width header="Meno"></vaadin-grid-filter-column>
  <vaadin-grid-filter-column id="surnameColumn" auto-width header="Priezvisko"></vaadin-grid-filter-column>
  <vaadin-grid-filter-column id="emailColumn" auto-width header="Email"></vaadin-grid-filter-column>
  <vaadin-grid-filter-column id="phoneColumn" auto-width header="Teleón"></vaadin-grid-filter-column>
  <vaadin-grid-filter-column id="personalNumberColumn" auto-width header="Rodné číslo"></vaadin-grid-filter-column>
  <vaadin-grid-filter-column id="icoColumn" auto-width header="IČO"></vaadin-grid-filter-column>
 </vaadin-grid>
</vaadin-vertical-layout>
`;
    }

    static get is() {
        return 'client-main-view';
    }

    static get properties() {
        return {
            // Declare your properties here.
        };
    }
}

customElements.define(ClientMainView.is, ClientMainView);
