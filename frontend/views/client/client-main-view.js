import {html, PolymerElement} from '@polymer/polymer/polymer-element.js';
import '@vaadin/vaadin-ordered-layout/src/vaadin-vertical-layout.js';
import '@vaadin/vaadin-grid/src/vaadin-grid.js';
import '@vaadin/vaadin-ordered-layout/src/vaadin-horizontal-layout.js';
import '@vaadin/vaadin-button/src/vaadin-button.js';

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
 <vaadin-horizontal-layout theme="spacing" style="width: 100%; justify-content: center; flex-wrap: wrap; align-content: center; flex-grow: 0; flex-shrink: 1; align-self: center;">
  <vaadin-horizontal-layout style="align-self: center;">
   <label id="label" style="align-self: center; flex-grow: 0; flex-shrink: 1;">Pridaj:</label>
  </vaadin-horizontal-layout>
  <vaadin-horizontal-layout style="align-items: center; align-self: stretch; flex-grow: 0; flex-wrap: nowrap; justify-content: center;" theme="spacing">
   <vaadin-button theme="primary" id="addPhysicalPersonButton" style="flex-grow: 0; flex-shrink: 0;">
     Fyzická osoba 
   </vaadin-button>
   <vaadin-button theme="primary" id="addSelfEmployedPersonButton" style="flex-grow: 0; flex-shrink: 0;">
    Živnostník
   </vaadin-button>
   <vaadin-button theme="primary" id="addClientCompanyButton" style="flex-grow: 0; flex-shrink: 0;">
     Spoločnosť 
   </vaadin-button>
  </vaadin-horizontal-layout>
 </vaadin-horizontal-layout>
 <vaadin-grid id="clientTable"></vaadin-grid>
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
