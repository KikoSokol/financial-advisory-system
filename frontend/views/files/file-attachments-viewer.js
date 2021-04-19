import {html, PolymerElement} from '@polymer/polymer/polymer-element.js';
import '@vaadin/vaadin-grid/src/vaadin-grid.js';

class FileAttachmentsViewer extends PolymerElement {

    static get template() {
        return html`
<style include="shared-styles">
                :host {
                    display: block;
                    height: 100%;
                }
            </style>
<vaadin-grid id="attachmentsTable"></vaadin-grid>
`;
    }

    static get is() {
        return 'file-attachments-viewer';
    }

    static get properties() {
        return {
            // Declare your properties here.
        };
    }
}

customElements.define(FileAttachmentsViewer.is, FileAttachmentsViewer);
