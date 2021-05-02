import {html, PolymerElement} from '@polymer/polymer/polymer-element.js';
import '@vaadin/vaadin-ordered-layout/src/vaadin-vertical-layout.js';
import '@vaadin/vaadin-ordered-layout/src/vaadin-horizontal-layout.js';
import '@vaadin/vaadin-button/src/vaadin-button.js';
import '@vaadin/vaadin-grid/src/vaadin-grid.js';

class ContractMainView extends PolymerElement {

    static get template() {
        return html`
<style include="shared-styles">
                :host {
                    display: block;
                    height: 100%;
                }
            </style>
<vaadin-vertical-layout theme="spacing" style="width: 100%; height: 100%;">
 <vaadin-horizontal-layout theme="spacing" style="width: 100%; align-self: center; justify-content: center; flex-wrap: wrap; align-content: center;">
  <vaadin-horizontal-layout theme="spacing" style="align-self: center;">
   <label>Pridaj:</label>
  </vaadin-horizontal-layout>
  <vaadin-horizontal-layout theme="spacing" style="align-self: stretch; align-items: center; justify-content: center; flex-wrap: nowrap;">
   <vaadin-button theme="primary" id="addLifeInsurance">
    Životné poistenie
   </vaadin-button>
   <vaadin-button theme="primary" id="addNonLifeInsurance">
    Neživotné poistenie
   </vaadin-button>
   <vaadin-button theme="primary" id="addVehicleInsurance">
    Poistenie auta
   </vaadin-button>
  </vaadin-horizontal-layout>
 </vaadin-horizontal-layout>
 <vaadin-grid id="contractTable"></vaadin-grid>
</vaadin-vertical-layout>
`;
    }

    static get is() {
        return 'contract-main-view';
    }

    static get properties() {
        return {
            // Declare your properties here.
        };
    }
}

customElements.define(ContractMainView.is, ContractMainView);
