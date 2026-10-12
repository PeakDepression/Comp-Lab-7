package se331.lab.dao;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;
import org.springframework.context.annotation.Profile;
import se331.lab.entity.Organizer;

import java.util.ArrayList;
import java.util.List;

@Repository
@Profile("memory")
public class OrganizerDaoImpl implements OrganizerDao {
    List<Organizer> organizerList;

    @PostConstruct
    public void init() {
        organizerList = new ArrayList<>();
        organizerList.add(Organizer.builder()
                .id(1L)
                .organizationName("Kat Laydee Sanctuary")
                .address("123 Cat Lane, Meow Town")
                .build());
        organizerList.add(Organizer.builder()
                .id(2L)
                .organizationName("Fern Pollin Community")
                .address("456 Garden Way, Flora City")
                .build());
        organizerList.add(Organizer.builder()
                .id(3L)
                .organizationName("Carey Saway Eco Org")
                .address("789 Beach Blvd, Playa Del Carmen")
                .build());
    }

    @Override
    public Integer getOrganizerSize() {
        return organizerList.size();
    }

    @Override
    public List<Organizer> getOrganizers(Integer pageSize, Integer page) {
        pageSize = pageSize == null ? organizerList.size() : pageSize;
        page = page == null ? 1 : page;
        int firstIndex = (page - 1) * pageSize;
        return organizerList.subList(firstIndex, Math.min(firstIndex + pageSize, organizerList.size()));
    }

    @Override
    public Organizer getOrganizer(Long id) {
        return organizerList.stream()
                .filter(organizer -> organizer.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Organizer save(Organizer organizer) {
        organizer.setId(organizerList.get(organizerList.size() - 1).getId() + 1);
        organizerList.add(organizer);
        return organizer;
    }
}