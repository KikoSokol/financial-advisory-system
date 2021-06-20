import {html, PolymerElement} from '@polymer/polymer/polymer-element.js';
import '@vaadin/vaadin-form-layout/src/vaadin-form-layout.js';
import '@vaadin/vaadin-text-field/src/vaadin-text-field.js';
import '@vaadin/vaadin-text-field/src/vaadin-text-area.js';
import '@vaadin/vaadin-checkbox/src/vaadin-checkbox.js';
import '@vaadin/vaadin-ordered-layout/src/vaadin-horizontal-layout.js';
import '@vaadin/vaadin-button/src/vaadin-button.js';
import '@polymer/iron-icon/iron-icon.js';

class TaskEditor extends PolymerElement {

    static get template() {
        return html`
<style include="shared-styles">
                :host {
                    display: block;
                    height: 100%;
                }
            </style>
<vaadin-form-layout style="width: 100%;">
 <vaadin-text-field label="Názov úlohy" placeholder="Názov úlohy" id="taskName" required></vaadin-text-field>
 <vaadin-horizontal-layout id="dateTimePlace" style="align-content: center;"></vaadin-horizontal-layout>
 <vaadin-text-area label="Poznámka" placeholder="Poznámka" id="description"></vaadin-text-area>
 <vaadin-checkbox id="done">
   Dokončena 
 </vaadin-checkbox>
 <label id="dateOfAdd">Label</label>
</vaadin-form-layout>
<vaadin-horizontal-layout style="width: 100%; justify-content: flex-end; flex-wrap: wrap; align-content: center;">
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
        return 'task-editor';
    }

    static get properties() {
        return {
            // Declare your properties here.
        };
    }
}

customElements.define(TaskEditor.is, TaskEditor);
