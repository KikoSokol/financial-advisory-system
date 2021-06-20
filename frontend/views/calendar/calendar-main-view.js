import {html, PolymerElement} from '@polymer/polymer/polymer-element.js';
import '@vaadin/vaadin-ordered-layout/src/vaadin-vertical-layout.js';
import '@vaadin/vaadin-ordered-layout/src/vaadin-horizontal-layout.js';
import '@vaadin/vaadin-button/src/vaadin-button.js';
import '@polymer/iron-icon/iron-icon.js';
import '@vaadin/vaadin-date-picker/src/vaadin-date-picker.js';

class CalendarMainView extends PolymerElement {

    static get template() {
        return html`
<style include="shared-styles">
                :host {
                    display: block;
                    height: 100%;
                }
            </style>
<vaadin-vertical-layout style="width: 100%; height: 100%;">
 <vaadin-vertical-layout theme="spacing" style="width: 100%;">
  <vaadin-horizontal-layout style="width: 100%; align-self: flex-start; flex-wrap: wrap; flex-direction: row; justify-content: center;">
   <vaadin-button theme="icon" aria-label="Add new" id="past">
    <iron-icon icon="lumo:angle-left"></iron-icon>
   </vaadin-button>
   <vaadin-date-picker id="chooseMonth"></vaadin-date-picker>
   <vaadin-button theme="icon" aria-label="Add new" id="future">
    <iron-icon icon="lumo:angle-right"></iron-icon>
   </vaadin-button>
  </vaadin-horizontal-layout>
  <label id="actualMonth" style="align-self: center;font-weight: bold;">Label</label>
 </vaadin-vertical-layout>
 <vaadin-horizontal-layout id="calendarPlace" style="width: 100%;"></vaadin-horizontal-layout>
 <vaadin-horizontal-layout style="width: 100%; flex-wrap: wrap; flex-direction: row; align-content: center;">
  <vaadin-button id="addMeeting" style="margin-right: var(--lumo-space-m);">
   <iron-icon icon="lumo:user" slot="prefix"></iron-icon>Pridaj stretnutie 
  </vaadin-button>
  <vaadin-button id="addTask">
   <iron-icon icon="lumo:edit" slot="prefix"></iron-icon>Pridaj úlohu 
  </vaadin-button>
 </vaadin-horizontal-layout>
</vaadin-vertical-layout>
`;
    }

    static get is() {
        return 'calendar-main-view';
    }

    static get properties() {
        return {
            // Declare your properties here.
        };
    }
}

customElements.define(CalendarMainView.is, CalendarMainView);
