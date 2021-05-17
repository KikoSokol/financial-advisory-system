import {html, PolymerElement} from '@polymer/polymer/polymer-element.js';
import '@vaadin/vaadin-form-layout/vaadin-form-layout.js';
import '@vaadin/vaadin-grid/src/vaadin-grid.js';
import '@vaadin/vaadin-text-field/src/vaadin-text-field.js';
import '@vaadin/vaadin-ordered-layout/src/vaadin-horizontal-layout.js';
import '@polymer/iron-icon/iron-icon.js';
import '@vaadin/vaadin-button/src/vaadin-button.js';

class SearchClientView extends PolymerElement {

    static get template() {
        return html`
<style include="shared-styles">
                :host {
                    display: block;
                    height: 100%;
                }
            </style>
<vaadin-form-layout colspan="2">
 <vaadin-text-field placeholder="Hľadaj" id="search" colspan="2">
  <iron-icon icon="lumo:search" slot="prefix"></iron-icon>
 </vaadin-text-field>
 <vaadin-grid id="tableClient" colspan="2"></vaadin-grid>
</vaadin-form-layout>
<vaadin-horizontal-layout style="width: 100%; height: 100%;">
 <vaadin-button theme="primary error" id="cancel" style="flex-grow: 1;">
   Zruš 
 </vaadin-button>
 <vaadin-button theme="primary success" id="add" style="flex-grow: 1; flex-shrink: 0;">
   Pridaj 
 </vaadin-button>
</vaadin-horizontal-layout>
`;
    }

    static get is() {
        return 'search-client-view';
    }

    static get properties() {
        return {
            // Declare your properties here.
        };
    }
}

customElements.define(SearchClientView.is, SearchClientView);
