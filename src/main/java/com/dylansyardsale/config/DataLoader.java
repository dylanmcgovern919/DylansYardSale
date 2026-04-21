package com.dylansyardsale.config;

import com.dylansyardsale.model.*;
import com.dylansyardsale.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// Loads required seed data for demo/testing //REQUIRED
@Configuration //REQUIRED
public class DataLoader {

    // COPILOT NOTE: Removed generateQR() method so Product uses DB-generated id only.

    @Bean //REQUIRED
    CommandLineRunner initData(ProductRepository productRepo, TagRepository tagRepo) {
        return args -> {
            if (productRepo.count() > 0) return;

            // ABOVE AND BEYOND PART: ADDED BY GOOGLE - Defensive check using findByName to prevent many-to-many data duplication
            Tag reggae = tagRepo.findByName("Reggae");
            if (reggae == null) reggae = tagRepo.save(new Tag("Reggae"));
            Tag punk = tagRepo.findByName("Punk");
            if (punk == null) punk = tagRepo.save(new Tag("Punk"));
            Tag metal = tagRepo.findByName("Metal");
            if (metal == null) metal = tagRepo.save(new Tag("Metal"));
            Tag hiphop = tagRepo.findByName("Hip-Hop");
            if (hiphop == null) hiphop = tagRepo.save(new Tag("Hip-Hop"));
            Tag dc = tagRepo.findByName("DC");
            if (dc == null) dc = tagRepo.save(new Tag("DC"));
            Tag image = tagRepo.findByName("Image");
            if (image == null) image = tagRepo.save(new Tag("Image"));

            // ABOVE AND BEYOND PART: ADDED BY GOOGLE - Automating unique SKU assignment for each initial inventory item
            // COPILOT NOTE: QR assignment lines removed; database id is now the only identifier.
            Product p1 = new Product("Vintage Burberry Baseball Hat", "", 45.0, ProductCategory.CLOTHING, null);
            productRepo.save(p1);

            Product p2 = new Product("Stone Island Black Cargo Pants", "", 98.0, ProductCategory.CLOTHING, null);
            productRepo.save(p2);

            Product p3 = new Product("Melvins 1993 Houdini Tour T-Shirt", "", 52.0, ProductCategory.CLOTHING, null);
            productRepo.save(p3);

            Product dekker = new Product("Desmond Dekker / Aces - 007 Shanty Town", "", 42.0, ProductCategory.RECORD, "Reggae");
            dekker.getTags().add(reggae);
            productRepo.save(dekker);

            Product minott = new Product("Sugar Minott / Rydim", "", 32.0, ProductCategory.RECORD, "Reggae");
            minott.getTags().add(reggae);
            productRepo.save(minott);

            Product na = new Product("Negative Approach / Tied Down", "", 25.0, ProductCategory.RECORD, "Punk");
            na.getTags().add(punk);
            productRepo.save(na);

            Product blitz = new Product("Blitz / Voice of a Generation", "", 27.0, ProductCategory.RECORD, "Punk");
            blitz.getTags().add(punk);
            productRepo.save(blitz);

            Product acid = new Product("Acid Bath / When the Kite String Pops", "", 65.0, ProductCategory.RECORD, "Metal");
            acid.getTags().add(metal);
            productRepo.save(acid);

            Product peeling = new Product("PeelingFlesh / G-Code", "", 27.0, ProductCategory.RECORD, "Metal");
            peeling.getTags().add(metal);
            productRepo.save(peeling);

            Product veeze = new Product("Veeze / Ganger", "", 70.0, ProductCategory.RECORD, "Hip-Hop");
            veeze.getTags().add(hiphop);
            productRepo.save(veeze);

            Product koolg = new Product("4, 5, 6 / Kool G Rap", "", 33.0, ProductCategory.RECORD, "Hip-Hop");
            koolg.getTags().add(hiphop);
            productRepo.save(koolg);

            Product swamp = new Product("Saga of the Swamp Thing, Book 1 by Alan Moore", "", 18.0, ProductCategory.COMIC, "DC");
            swamp.getTags().add(dc);
            productRepo.save(swamp);

            Product assassin = new Product("Assassin Nation Full Series 1-5 by Kyle Starks", "", 15.0, ProductCategory.COMIC, "Image");
            assassin.getTags().add(image);
            productRepo.save(assassin);
        };
    }
}
