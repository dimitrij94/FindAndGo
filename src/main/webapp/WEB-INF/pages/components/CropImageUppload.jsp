

    <style type="text/css">
        #photo-input-wrapper {
            height: 420px;
        }

        #image {
            max-width: 100%;
            max-height: 90%;
            display: block;
            margin-left: auto;
            margin-right: auto
        }

        #upload {
            position: relative;
            background-color: #ddd;
            margin: 8px 0 0 -132px;
            left: 50%;
        }
    </style>


        <div id="photo-input-wrapper" class="thumbnail col-xs-12 col-md-6 col-lg-4">
            <img src="http://placehold.it/1600x900" id="image"/>

            <form action="/uploadImage" method="post" enctype="multipart/form-data">
                <input id="upload" type="file" name="upload"/>
                <input name="x" type="hidden" id="x"/>
                <input name="y" type="hidden" id="y"/>
                <input name="h" type="hidden" id="h"/>
                <input name="w" type="hidden" id="w"/>
                <input type="submit">
            </form>
        </div>


