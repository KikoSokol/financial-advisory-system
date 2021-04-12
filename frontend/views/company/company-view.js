import {html, PolymerElement} from '@polymer/polymer/polymer-element.js';
import '@vaadin/vaadin-ordered-layout/src/vaadin-vertical-layout.js';
import '@vaadin/vaadin-button/src/vaadin-button.js';
import '@polymer/iron-icon/iron-icon.js';
import '@vaadin/vaadin-grid/src/vaadin-grid.js';
import '@vaadin/vaadin-ordered-layout/src/vaadin-horizontal-layout.js';

class CompanyView extends PolymerElement {

    static get template() {
        return html`
<style include="shared-styles">
                :host {
                    display: block;
                    height: 100%;
                }
            </style>
<vaadin-vertical-layout>
 <vaadin-horizontal-layout theme="spacing" style="width: 100%; justify-content: center;">
  <h3 style="flex-shrink: 1;">Spoločnosti </h3>
 </vaadin-horizontal-layout>
 <vaadin-horizontal-layout style="width: 100%; justify-content: center; flex-grow: 0; flex-shrink: 1;">
  <vaadin-button id="addCompany">
   <iron-icon icon="lumo:plus" slot="prefix"></iron-icon>Pridaj spoločnosť 
  </vaadin-button>
 </vaadin-horizontal-layout>
 <vaadin-horizontal-layout style="width: 100%; justify-content: center;">
  <vaadin-grid id="companyTable"></vaadin-grid>
 </vaadin-horizontal-layout>
</vaadin-vertical-layout>
`;
    }

    static get is() {
        return 'company-view';
    }

    static get properties() {
        return {
            // Declare your properties here.
        };
    }
}

customElements.define(CompanyView.is, CompanyView);
