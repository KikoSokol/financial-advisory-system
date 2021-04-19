import {html, PolymerElement} from '@polymer/polymer/polymer-element.js';
import '@vaadin/vaadin-ordered-layout/src/vaadin-vertical-layout.js';
import '@vaadin/vaadin-grid/src/vaadin-grid.js';
import '@vaadin/vaadin-button/src/vaadin-button.js';
import '@polymer/iron-icon/iron-icon.js';

class FileAttachmentsAddView extends PolymerElement {

    static get template() {
        return html`
<style include="shared-styles">
                :host {
                    display: block;
                    height: 100%;
                }
            </style>
<vaadin-vertical-layout>
 <vaadin-grid id="uploadedFileTable"></vaadin-grid>
 <vaadin-button theme="primary" id="addFile" style="align-self: center;">
  <iron-icon icon="lumo:plus"></iron-icon>Pridaj súbor
 </vaadin-button>
</vaadin-vertical-layout>
`;
    }

    static get is() {
        return 'file-attachments-add-view';
    }

    static get properties() {
        return {
            // Declare your properties here.
        };
    }
}

customElements.define(FileAttachmentsAddView.is, FileAttachmentsAddView);
