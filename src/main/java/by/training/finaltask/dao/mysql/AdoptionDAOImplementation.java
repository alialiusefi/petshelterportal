package by.training.finaltask.dao.mysql;

import by.training.finaltask.dao.AdoptionDAO;
import by.training.finaltask.entity.Adoption;
import by.training.finaltask.exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

public final class AdoptionDAOImplementation extends BaseDAO implements AdoptionDAO {

	private static final int COUNTARGUMENTS = 10;
	private Logger logger = LogManager.getLogger(AdoptionDAOImplementation.class);

	public AdoptionDAOImplementation(Connection connection) {
		super(connection);
	}

	@Override
	public Adoption get(Integer adoptionID) throws PersistentException {
		try (PreparedStatement preparedStatement = connection.prepareStatement(
				resourceBundle.getString("getAdoptionDAO"))) {
			preparedStatement.setInt(1, adoptionID);
			try (ResultSet resultset = preparedStatement.executeQuery()) {
				if (resultset.next()) {
					return getAdoption(resultset);
				}

			}
		} catch (SQLException e) {
			logger.warn(e.getMessage(), e);
			throw new PersistentException(e.getMessage(), e);
		}
		return null;
	}

	@Override
	public List<Adoption> getAll(int offset, int rowcount) throws PersistentException {
		try (PreparedStatement preparedStatement = connection.prepareStatement(
				resourceBundle.getString("getAllAdoptionDAO"))) {
			preparedStatement.setInt(1, offset);
			preparedStatement.setInt(2, rowcount);
			List<Adoption> adoptions = new LinkedList<>();
			try (ResultSet resultset = preparedStatement.executeQuery()) {
				while (resultset.next()) {
					adoptions.add(getAdoption(resultset));
				}

			}
			return adoptions;

		} catch (SQLException e) {
			logger.warn(e.getMessage(), e);
			throw new PersistentException(e.getMessage(), e);
		}
	}

	@Override
	public List<Adoption> getAllByPetID(Integer petID, int offset, int rowcount) throws PersistentException {
		try (PreparedStatement preparedStatement = connection.prepareStatement(
				resourceBundle.getString("getAllAdoptionDAO"))) {
			List<Adoption> adoptions = new LinkedList<>();
			preparedStatement.setInt(1, petID);
			try (ResultSet resultset = preparedStatement.executeQuery()) {
				while (resultset.next()) {
					adoptions.add(getAdoption(resultset));
				}

			}
			return adoptions;

		} catch (SQLException e) {
			logger.warn(e.getMessage(), e);
			throw new PersistentException(e.getMessage(), e);
		}
	}

	@Override
	public List<Adoption> getAllBetweenDates(GregorianCalendar start, GregorianCalendar end, int offset, int rowcount)
			throws PersistentException {
		try (PreparedStatement preparedStatement = connection.prepareStatement(
				resourceBundle.getString("getAllAdoptionBetweenDatesDAO"))) {
			List<Adoption> adoptions = new LinkedList<>();
			Date startDate = new Date(start.getTimeInMillis());
			Date endDate = new Date(end.getTimeInMillis());
			preparedStatement.setDate(1, startDate);
			preparedStatement.setDate(2, endDate);
			preparedStatement.setDate(3, startDate);
			preparedStatement.setDate(4, endDate);
			preparedStatement.setDate(5, startDate);
			preparedStatement.setDate(6, endDate);
			preparedStatement.setInt(7, offset);
			preparedStatement.setInt(8, rowcount);
			try (ResultSet resultset = preparedStatement.executeQuery()) {
				while (resultset.next()) {
					adoptions.add(getAdoption(resultset));
				}

			}
			return adoptions;

		} catch (SQLException e) {
			logger.warn(e.getMessage(), e);
			throw new PersistentException(e.getMessage(), e);
		}
	}

	@Override
	public int getCountBetweenDates(GregorianCalendar start, GregorianCalendar end) throws PersistentException {
		try (PreparedStatement statement = connection.prepareStatement(
				resourceBundle.getString("getCountAdoptionBetweenDatesDAO"))) {
			Date startDate = new Date(start.getTimeInMillis());
			Date endDate = new Date(end.getTimeInMillis());
			statement.setDate(1, startDate);
			statement.setDate(2, endDate);
			statement.setDate(3, startDate);
			statement.setDate(4, endDate);
			statement.setDate(5, startDate);
			statement.setDate(6, endDate);
			try (ResultSet set = statement.executeQuery()) {
				set.next();
				return set.getInt(1);
			}

		} catch (SQLException e) {
			logger.warn(e.getMessage(), e);
			throw new PersistentException(e.getMessage(), e);
		}
	}

	@Override
	public List<Adoption> getAllBetweenDatesCurrentUser(int userID, GregorianCalendar start, GregorianCalendar end,
			int offset, int rowcount) throws PersistentException {
		try (PreparedStatement preparedStatement = connection.prepareStatement(
				resourceBundle.getString("getAllAdoptionBetweenDatesCurrentUserDAO"))) {
			List<Adoption> adoptions = new LinkedList<>();
			Date startDate = new Date(start.getTimeInMillis());
			Date endDate = new Date(end.getTimeInMillis());
			preparedStatement.setDate(1, startDate);
			preparedStatement.setDate(2, endDate);
			preparedStatement.setDate(3, startDate);
			preparedStatement.setDate(4, endDate);
			preparedStatement.setDate(5, startDate);
			preparedStatement.setDate(6, endDate);
			preparedStatement.setInt(7, userID);
			preparedStatement.setInt(8, offset);
			preparedStatement.setInt(9, rowcount);
			try (ResultSet resultset = preparedStatement.executeQuery()) {
				while (resultset.next()) {
					adoptions.add(getAdoption(resultset));
				}
			}
			return adoptions;

		} catch (SQLException e) {
			logger.warn(e.getMessage(), e);
			throw new PersistentException(e.getMessage(), e);
		}
	}

	@Override
	public List<Adoption> getAllPetName(String petName, int offset, int rowcount) throws PersistentException {
		try (PreparedStatement preparedStatement = connection.prepareStatement(
				resourceBundle.getString("getAllAdoptionPetNameDAO"))) {
			List<Adoption> adoptions = new LinkedList<>();
			preparedStatement.setNString(1, petName);
			preparedStatement.setInt(2, offset);
			preparedStatement.setInt(3, rowcount);
			try (ResultSet resultset = preparedStatement.executeQuery()) {
				while (resultset.next()) {
					adoptions.add(getAdoption(resultset));
				}

			}
			return adoptions;

		} catch (SQLException e) {
			logger.warn(e.getMessage(), e);
			throw new PersistentException(e.getMessage(), e);
		}
	}

	@Override
	public List<Adoption> getAllPetNameCurrentUser(int userID, String petName, int offset, int rowcount)
			throws PersistentException {
		try (PreparedStatement preparedStatement = connection.prepareStatement(
				resourceBundle.getString("getAllAdoptionPetNameCurrentUserDAO"))) {
			List<Adoption> adoptions = new LinkedList<>();
			preparedStatement.setNString(1, petName);
			preparedStatement.setInt(2, userID);
			preparedStatement.setInt(3, offset);
			preparedStatement.setInt(4, rowcount);
			try (ResultSet resultset = preparedStatement.executeQuery()) {
				while (resultset.next()) {
					adoptions.add(getAdoption(resultset));
				}
				return adoptions;
			}
		} catch (SQLException e) {
			logger.warn(e.getMessage(), e);
			throw new PersistentException(e.getMessage(), e);
		}
	}

	@Override
	public List<Adoption> getAllCurrentUser(int userID, int offset, int rowcount) throws PersistentException {
		try (PreparedStatement preparedStatement = connection.prepareStatement(
				resourceBundle.getString("getAllAdoptionCurrentUserDAO"))) {
			List<Adoption> adoptions = new LinkedList<>();
			preparedStatement.setInt(1, userID);
			preparedStatement.setInt(2, offset);
			preparedStatement.setInt(3, rowcount);
			try (ResultSet resultset = preparedStatement.executeQuery()) {
				while (resultset.next()) {
					adoptions.add(getAdoption(resultset));
				}
				return adoptions;
			}
		} catch (SQLException e) {
			logger.warn(e.getMessage(), e);
			throw new PersistentException(e.getMessage(), e);
		}
	}

	@Override
	public int getAllCountCurrentUser(int userID) throws PersistentException {
		try (PreparedStatement statement = connection.prepareStatement(
				resourceBundle.getString("getAllCountAdoptionCurrentUserDAO"))) {
			statement.setInt(1, userID);
			try (ResultSet set = statement.executeQuery()) {
				set.next();
				return set.getInt(1);
			}
		} catch (SQLException e) {
			logger.warn(e.getMessage(), e);
			throw new PersistentException(e.getMessage(), e);
		}
	}

	@Override
	public int getCountPetName(String petName) throws PersistentException {
		try (PreparedStatement statement = connection.prepareStatement(
				resourceBundle.getString("getCountAdoptionPetNameDAO"))) {
			statement.setNString(1, petName);
			try (ResultSet set = statement.executeQuery()) {
				set.next();
				return set.getInt(1);
			}
		} catch (SQLException e) {
			logger.warn(e.getMessage(), e);
			throw new PersistentException(e.getMessage(), e);
		}
	}

	@Override
	public int getCountPetNameCurrentUser(String petName, int userID) throws PersistentException {
		try (PreparedStatement statement = connection.prepareStatement(
				resourceBundle.getString("getCountAdoptionPetNameCurrentUserDAO"))) {
			statement.setNString(1, petName);
			statement.setInt(2, userID);
			try (ResultSet set = statement.executeQuery()) {
				set.next();
				return set.getInt(1);
			}
		} catch (SQLException e) {
			logger.warn(e.getMessage(), e);
			throw new PersistentException(e.getMessage(), e);
		}
	}

	@Override
	public int getCountBetweenDatesCurrentUser(int userID, GregorianCalendar start, GregorianCalendar end)
			throws PersistentException {
		try (PreparedStatement statement = connection.prepareStatement(
				resourceBundle.getString("getCountAdoptionBetweenDatesCurrentUserDAO"))) {
			Date startDate = new Date(start.getTimeInMillis());
			Date endDate = new Date(end.getTimeInMillis());
			statement.setDate(1, startDate);
			statement.setDate(2, endDate);
			statement.setDate(3, startDate);
			statement.setDate(4, endDate);
			statement.setDate(5, startDate);
			statement.setDate(6, endDate);
			statement.setInt(7, userID);
			try (ResultSet set = statement.executeQuery()) {
				if (set.next()) {
					return set.getInt(1);
				}

			}
			return 0;
		} catch (SQLException e) {
			logger.warn(e.getMessage(), e);
			throw new PersistentException(e.getMessage(), e);
		}
	}

	@Override
	public int getCountByPetIDandDateNotNull(int petID, GregorianCalendar start, GregorianCalendar end)
			throws PersistentException {
		try (PreparedStatement preparedStatement = connection.prepareStatement(
				resourceBundle.getString("getAmountByPetIDandAdoptionDate"))) {
			preparedStatement.setInt(1, petID);
			for (int i = 2; i <= COUNTARGUMENTS; i++) {
				if (i == COUNTARGUMENTS) {
					preparedStatement.setDate(i, new Date(end.getTimeInMillis()));
					continue;
				}
				if (i % 2 == 0) {
					preparedStatement.setDate(i, new Date(start.getTimeInMillis()));
					continue;
				}
				preparedStatement.setDate(i, new Date(end.getTimeInMillis()));
			}
			int res = 0;
			try (ResultSet set = preparedStatement.executeQuery()) {
				if (set.next()) {
					res = set.getInt(1);
				}
			}
			return res;
		} catch (SQLException e) {
			logger.warn(e.getMessage(), e);
			throw new PersistentException(e.getMessage(), e);
		}
	}

	@Override
	public int getCountByPetIDandDateNull(int petID, GregorianCalendar start) throws PersistentException {
		try (PreparedStatement preparedStatement = connection.prepareStatement(
				resourceBundle.getString("getAmountByPetIDandAdoptionDateNull"))) {
			preparedStatement.setInt(1, petID);
			preparedStatement.setDate(2, new Date(start.getTimeInMillis()));
			preparedStatement.setDate(3, new Date(start.getTimeInMillis()));
			preparedStatement.setDate(4, new Date(start.getTimeInMillis()));
			int res = 0;
			try (ResultSet set = preparedStatement.executeQuery()) {
				if (set.next()) {
					res = set.getInt(1);
				}
			}
			return res;
		} catch (SQLException e) {
			logger.warn(e.getMessage(), e);
			throw new PersistentException(e.getMessage(), e);
		}
	}

	@Override
	public int getAllCount() throws PersistentException {
		try (PreparedStatement statement = connection.prepareStatement(
				resourceBundle.getString("getAllAmountAdoptionDAO"))) {
			try (ResultSet set = statement.executeQuery()) {
				if (set.next()) {
					return set.getInt(1);
				}
			}
			return 0;

		} catch (SQLException e) {
			logger.warn(e.getMessage(), e);
			throw new PersistentException(e.getMessage(), e);
		}
	}

	@Override
	public int add(Adoption element) throws PersistentException {
		try (PreparedStatement preparedStatement = connection.prepareStatement(
				resourceBundle.getString("addAdoptionDAO"), PreparedStatement.RETURN_GENERATED_KEYS)) {
			preparedStatement.setInt(1, element.getPetID());
			Date sqlDateAdoptionStart = new Date(element.getAdoptionStart().getTimeInMillis());
			preparedStatement.setDate(2, sqlDateAdoptionStart);
			if (element.getAdoptionEnd() != null) {
				Date sqlDateAdoptionEnd = new Date(element.getAdoptionEnd().getTimeInMillis());
				preparedStatement.setDate(3, sqlDateAdoptionEnd);
			} else {
				preparedStatement.setNull(3, Types.DATE);
			}
			preparedStatement.setInt(4, element.getUserID());
			preparedStatement.executeUpdate();
			try (ResultSet set = preparedStatement.getGeneratedKeys()) {
				set.next();
				return set.getInt(1);
			}

		} catch (SQLException e) {
			logger.warn(e.getMessage(), e);
			throw new PersistentException("Couldn't register row!\n" + e.getMessage(), e);
		}
	}

	@Override
	public boolean update(Adoption element) throws PersistentException {
		try (PreparedStatement preparedStatement = connection.prepareStatement(
				resourceBundle.getString("updateAdoptionDAO"))) {
			preparedStatement.setInt(1, element.getPetID());
			Date sqlDateAdoptionStart = new Date(element.getAdoptionStart().getTimeInMillis());
			Date sqlDateAdoptionend = null;
			if (element.getAdoptionEnd() != null) {
				sqlDateAdoptionend = new Date(element.getAdoptionEnd().getTimeInMillis());
			}
			preparedStatement.setDate(2, sqlDateAdoptionStart);
			preparedStatement.setDate(3, sqlDateAdoptionend);
			preparedStatement.setInt(4, element.getUserID());
			preparedStatement.setInt(5, element.getId());
			preparedStatement.executeUpdate();
			return true;
		} catch (SQLException e) {
			logger.warn(e.getMessage(), e);
			throw new PersistentException("Couldn't update user!\n" + e.getMessage(), e);
		}
	}

	@Override
	public boolean delete(Adoption adoption) throws PersistentException {
		return delete(adoption.getPetID());
	}

	@Override
	public boolean delete(int adoptionID) throws PersistentException {
		try (PreparedStatement preparedStatement = connection.prepareStatement(
				resourceBundle.getString("deleteAdoptionDAO"))) {
			preparedStatement.setInt(1, adoptionID);
			preparedStatement.executeUpdate();
			return true;
		} catch (SQLException e) {
			logger.warn(e.getMessage(), e);
			throw new PersistentException(e.getMessage(), e);
		}
	}

	private Adoption getAdoption(ResultSet resultSet) throws SQLException {
		int id = resultSet.getInt("id");
		int petID = resultSet.getInt("pet_id");
		GregorianCalendar adoptionStart = new GregorianCalendar();
		adoptionStart.setTime(resultSet.getDate("adoption_start"));
		GregorianCalendar adoptionEndCal = new GregorianCalendar();
		Date adoptionEndDate = resultSet.getDate("adoption_end");
		if (adoptionEndDate != null) {
			adoptionEndCal.setTime(adoptionEndDate);
		} else {
			adoptionEndCal = null;
		}
		int userID = resultSet.getInt("user_id");
		return new Adoption(id, petID, adoptionStart, adoptionEndCal, userID);
	}
}
