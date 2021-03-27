import {html, PolymerElement} from '@polymer/polymer/polymer-element.js';
import '@vaadin/vaadin-form-layout/src/vaadin-form-layout.js';
import '@vaadin/vaadin-button/src/vaadin-button.js';
import '@polymer/iron-icon/iron-icon.js';
import '@vaadin/vaadin-ordered-layout/src/vaadin-vertical-layout.js';

class PersonalCardComponent extends PolymerElement {

    static get template() {
        return html`
<style include="shared-styles">
                :host {
                    display: block;
                    height: 100%;
                }
            </style>
<vaadin-form-layout style="width: 100%; height: 100%;">
 <vaadin-vertical-layout style="flex-grow: 0; flex-shrink: 1; align-self: flex-start;" id="frontSideLayout">
  <h3 style="align-self: center;">Predná strana</h3>
  <span id="frontSideName" style="align-self: center;">Názov</span>
  <vaadin-button id="downloadFrontSide" style="align-self: center;">
   <iron-icon icon="lumo:download" slot="prefix"></iron-icon>Stiahni 
  </vaadin-button>
 </vaadin-vertical-layout>
 <vaadin-vertical-layout style="flex-grow: 0; flex-shrink: 1; align-self: flex-start;" id="backSideLayout">
  <h3 style="align-self: center;">Zadná strana</h3>
  <span style="align-self: center;" id="backSideName">Názov</span>
  <vaadin-button id="downloadBackSide" style="align-self: center;">
   <iron-icon icon="lumo:download" slot="prefix"></iron-icon>Stiahni 
  </vaadin-button>
 </vaadin-vertical-layout>
</vaadin-form-layout>
`;
    }

    static get is() {
        return 'personal-card-component';
    }

    static get properties() {
        return {
            // Declare your properties here.
        };
    }
}

customElements.define(PersonalCardComponent.is, PersonalCardComponent);
