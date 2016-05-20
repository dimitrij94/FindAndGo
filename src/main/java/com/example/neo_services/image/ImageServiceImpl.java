package com.example.neo_services.image;

public class ImageServiceImpl  {
/*
    private static String IMAGE_ROOT_LOCATION = "Z:\\home\\localhost\\KeyStyle\\images\\";

    @Autowired
    public ImageServiceImpl(PlacePhotoRepository placePhotoRepository,
                            PlaceServicePhotoRepository placeServicePhotoRepository,
                            GraphDatabase db,
                            MyExecutorService threadPoolContainer,
                            PlaceUserPhotoRepository placeUserPhotoRepository) {
        this.placePhotoRepository = placePhotoRepository;
        this.placeServicePhotoRepository = placeServicePhotoRepository;
        this.db = db;
        this.threadPoolContainer = threadPoolContainer;
        this.placeUserPhotoRepository = placeUserPhotoRepository;
    }


    private MyExecutorService threadPoolContainer;
    private PlacePhotoRepository placePhotoRepository;
    private PlaceServicePhotoRepository placeServicePhotoRepository;
    private PlaceUserPhotoRepository placeUserPhotoRepository;
    private GraphDatabase db;


    @Override
    public void savePhoto(PhotoDTO photoDTO, PlaceUser placeUser) throws IOException, BadHttpRequest {
        CommonsMultipartFile imageFile = photoDTO.getImage();
        BufferedImage image = ImageIO.read(imageFile.getInputStream());
        ImageSize[] sizes = UserImageSizes.values();

        try (Transaction tx = db.beginTx()) {
            PlaceUserPhoto photo = new PlaceUserPhoto(image.getWidth(), image.getHeight(), ImageSize.MAIN_PHOTO_NAME);
            photo.user(placeUser);
            placeUserPhotoRepository.save(photo);

            for (ImageSize size : sizes) {
                photo = (PlaceUserPhoto) photo.width(size.getWidth()).heigth(size.getHeight()).name(size.getName());
                placeUserPhotoRepository.save(photo);
            }
            tx.success();
        }
        upload(image, placeUser, ImageContainerType.PLACE_USER, imageFile.getContentType());

    }

    @Override
    public void savePlaceMenuServicePhoto(PhotoDTO photoDTO, PlaceMenuService service) throws IOException, BadHttpRequest {
        CommonsMultipartFile imageFile = photoDTO.getImage();
        BufferedImage image = ImageIO.read(imageFile.getInputStream());
        PlaceMenuServicePhoto photo = new PlaceMenuServicePhoto();
        photo.service(service).width(image.getWidth()).heigth(image.getHeight()).name(ImageSize.MAIN_PHOTO_NAME);

        try (Transaction tx = db.beginTx()) {
            placeServicePhotoRepository.save(photo);

            for (ImageSize size : MenuImageSizes.values()) {
                photo = (PlaceMenuServicePhoto) photo.name(size.getName()).width(size.getWidth()).heigth(size.getHeight());
                placeServicePhotoRepository.save(photo);
            }
            tx.success();
        }
        upload(image, service, ImageContainerType.PLACE_MENU, imageFile.getContentType());
    }


    @Override
    public void savePlacePhoto(PhotoDTO photoDTO, Place place) throws IOException, BadHttpRequest {
        CommonsMultipartFile photoFile = photoDTO.getImage();

        BufferedImage image = ImageIO.read(photoFile.getInputStream());
        upload(image, place, ImageContainerType.PLACE, photoFile.getContentType());

        PlacePhoto photo = new PlacePhoto();
        photo = (PlacePhoto) photo.place(place).name(ImageSize.MAIN_PHOTO_NAME).width(image.getWidth()).heigth(image.getHeight());

        try (Transaction tx = db.beginTx()) {
            placePhotoRepository.save(photo);
            for (ImageSize size : PlaceImageSizes.values()) {
                photo = (PlacePhoto) photo.name(size.getName()).width(size.getWidth()).heigth(size.getHeight());
                photo = placePhotoRepository.save(photo);
            }
            tx.success();
        }
    }

    private void upload(BufferedImage image,
                        PhotoContainable domain,
                        ImageContainerType type,
                        String contentType) throws BadHttpRequest, IOException {

        ExecutorService threadPool = threadPoolContainer.getExecutor();
        String mainPath = getImageLocation(contentType, type, domain, ImageSize.MAIN_PHOTO_NAME);
        savePhoto(image, mainPath, contentType);

        final BufferedImage finalBufferedImage = getPhoto(type, domain, ImageSize.MAIN_PHOTO_NAME, contentType);

        for (ImageSize size : type.getSizes()) {
            threadPool.execute(() -> {
                BufferedImage bufferedImage = null;
                try {
                    int imageWidth = finalBufferedImage.getWidth();
                    bufferedImage = imageWidth <= size.getWidth() ?
                            finalBufferedImage :
                            scaleImage(finalBufferedImage, size);
                    String path = getImageLocation(contentType, type, domain, size.getName());
                    savePhoto(bufferedImage, path, contentType);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }


    @Override
    public BufferedImage getPhoto(ImageContainerType type, PhotoContainable user, String name, String contentType) throws IOException {
        File file = new File(getImageLocation(contentType, type, user, name));
        return ImageIO.read(file);
    }

    @Override
    public PlaceUserPhoto getPhoto(String photoName) {
        return placeUserPhotoRepository.findByName(photoName);
    }

    private void savePhoto(InputStream imageInputStram, String path) {
        File file = new File(path);
        try (BufferedInputStream in = new BufferedInputStream(imageInputStram);
             BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file))) {

            int bufferSize = 8192;
            byte[] buffer = new byte[bufferSize];
            int offset = 0;
            int bytesRead;

            while ((bytesRead = in.read(buffer, offset, bufferSize)) != -1) {
                out.write(buffer, offset, bytesRead);
                offset += bytesRead;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getImageLocation(String contentType, ImageContainerType type, PhotoContainable container, String name) {
        String imageType = getFileExtention(contentType);
        return IMAGE_ROOT_LOCATION + type.getLocation() + "//" + container.getUserName() + "//" + name + "." + imageType;
    }

    private String getImageLocation(ImageContainerType type, PhotoContainable container) {
        return IMAGE_ROOT_LOCATION + type.getLocation() + "//" + container.getUserName() + "//";
    }

    private String getFileExtention(String contentType) {
        return contentType.split("/")[1];
    }

    private void savePhoto(BufferedImage image, String path, String contentType) throws IOException {
        ImageIO.write(image, getFileExtention(contentType), new File(path));
    }


    private BufferedImage getReadableImage(byte[] imageBody) {
        InputStream inputStream = new ByteArrayInputStream(imageBody);
        try {
            return ImageIO.read(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private BufferedImage cropImage(BufferedImage bufferedImage, PhotoDTO image) {
        int x = Math.round(image.getX());
        int y = Math.round(image.getY());
        int w = Math.round(image.getW());
        int h = Math.round(image.getH());
        return bufferedImage.getSubimage(x, y, w, h);
    }

    private BufferedImage scaleImage(BufferedImage originImage, ImageSize size) {
        int type = originImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originImage.getType();
        BufferedImage changedImage = new BufferedImage(size.getWidth(), size.getHeight(), type);
        Graphics2D g = changedImage.createGraphics();
        g.drawImage(originImage, 0, 0, size.getWidth(), size.getHeight(), null);
        g.dispose();
        g.setComposite(AlphaComposite.Src);

        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        return changedImage;
    }

    private byte[] convertImage(BufferedImage image) throws IOException {
        try (ByteArrayOutputStream baoStream = new ByteArrayOutputStream()) {
            ImageIO.write(image, "jpg", baoStream);
            baoStream.flush();
            return baoStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new IOException("failed to read bytes from image");
    }

*/
}
