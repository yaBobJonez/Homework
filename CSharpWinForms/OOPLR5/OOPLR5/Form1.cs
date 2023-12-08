using Microsoft.VisualBasic;
using OOPLR4;
using System.Drawing;
using System.Windows.Forms;

namespace OOPLR2
{
    public partial class Form1 : Form
    {
        JuniorMagazine? mag1 = null, mag2 = null;

        public Form1()
        {
            InitializeComponent();
            new Form2().Show();
        }

        private JuniorMagazine createNewMagazine()
        {
            int inIssue, inPeriod, inPages, inWPP, inNOfImages, inNOfAds, inNOfActivities; string inTitle, inAuthor;
            while (!int.TryParse(Interaction.InputBox("������ ������", "��������"), out inIssue)) ;
            while (!int.TryParse(Interaction.InputBox("������ ����������� (� ����)", "��������"), out inPeriod)) ;
            while (!int.TryParse(Interaction.InputBox("������ ������� �������", "��������"), out inPages)) ;
            while (!int.TryParse(Interaction.InputBox("������ ������� ��� �� �������", "��������"), out inWPP)) ;
            while (!int.TryParse(Interaction.InputBox("������ ������� ���������", "��������"), out inNOfImages)) ;
            while (!int.TryParse(Interaction.InputBox("������ ������� ������", "��������"), out inNOfAds)) ;
            while (!int.TryParse(Interaction.InputBox("������ ������� �����������", "��������"), out inNOfActivities)) ;
            inTitle = Interaction.InputBox("������ �����", "��������");
            inAuthor = Interaction.InputBox("������ ������", "��������");
            return new JuniorMagazine(inIssue, inPeriod, inPages, inWPP, inNOfImages, inNOfAds, inNOfActivities, inTitle, inAuthor);
        }

        private void OnJ1Create(object sender, EventArgs e)
        {
            mag1 = createNewMagazine();
            j1TimeBtn.Enabled = true;
            j1WordsBtn.Enabled = true;
            j1InfoBtn.Enabled = true;
            j1DestructBtn.Enabled = true;
        }

        private void OnJ2Create(object sender, EventArgs e)
        {
            mag2 = createNewMagazine();
            j2TimeBtn.Enabled = true;
            j2WordsBtn.Enabled = true;
            j2InfoBtn.Enabled = true;
            j2DestructBtn.Enabled = true;
        }

        private void OnJ1Time(object sender, EventArgs e)
        {
            int issue;
            while (!int.TryParse(Interaction.InputBox("������ � �������", "��������"), out issue)) ;
            MessageBox.Show($"ʳ������ ��� �� �������: {mag1?.RelativeTimeTillIssueNumber(issue)}", "���������");
        }

        private void OnJ2Time(object sender, EventArgs e)
        {
            int issue;
            while (!int.TryParse(Interaction.InputBox("������ � �������", "��������"), out issue)) ;
            MessageBox.Show($"ʳ������ ��� �� �������: {mag2?.RelativeTimeTillIssueNumber(issue)}", "���������");
        }

        private void OnJ1Words(object sender, EventArgs e)
        {
            MessageBox.Show($"ʳ������ ���: {mag1?.WordCount()}", "���������");
        }

        private void OnJ2Words(object sender, EventArgs e)
        {
            MessageBox.Show($"ʳ������ ���: {mag1?.WordCount()}", "���������");
        }

        private void OnJ1Info(object sender, EventArgs e)
        {
            mag1?.Info();
        }

        private void OnJ2Info(object sender, EventArgs e)
        {
            mag2?.Info();
        }

        private void OnJ1Destruct(object sender, EventArgs e)
        {
            mag1 = null;
            j1TimeBtn.Enabled = false;
            j1WordsBtn.Enabled = false;
            j1InfoBtn.Enabled = false;
            j1DestructBtn.Enabled = false;
        }

        private void OnJ2Destruct(object sender, EventArgs e)
        {
            mag2 = null;
            j2TimeBtn.Enabled = false;
            j2WordsBtn.Enabled = false;
            j2InfoBtn.Enabled = false;
            j2DestructBtn.Enabled = false;
        }

        private void OnQuit(object sender, EventArgs e)
        {
            Application.Exit();
        }

        private void OnPeriodRepresent(object sender, EventArgs e)
        {
            int temp;
            while (!int.TryParse(Interaction.InputBox("������ ����� � ���� ������", "��������"), out temp)) ;
            MessageBox.Show(new JuniorMagazine()[temp], "�������� �����");
        }
    }

    public abstract class PeriodicalPublication
    {
        public static int Count = 0;
        public int Nomer;

        protected int Issue, PeriodInDays = 7, NumberOfPages, AvgWordsPerPage;
        protected string Title, Author = "��� ������";

        public PeriodicalPublication(int issue, int periodInDays, int numberOfPages, int avgWordsPerPage, string title, string author)
        {
            Issue = Math.Abs(issue);
            PeriodInDays = Math.Abs(periodInDays);
            AvgWordsPerPage = Math.Abs(avgWordsPerPage);
            NumberOfPages = Math.Abs(numberOfPages);
            Title = title;
            Author = author;
            Nomer = ++Count;
        }
        public PeriodicalPublication(int issue, int numberOfPages, string title)
        {
            Issue = Math.Abs(issue);
            NumberOfPages = Math.Abs(numberOfPages);
            Title = title;
            Nomer = ++Count;
        }
        public PeriodicalPublication()
        {
            Issue = 1;
            NumberOfPages = 25;
            Title = "�������� �������";
            Nomer = ++Count;
        }

        ~PeriodicalPublication()
        {
            MessageBox.Show($"��������� ��������� ������� �{Nomer}.");
            --Count;
        }

        public abstract int RelativeTimeTillIssueNumber(int nextIssue);
        public abstract int WordCount();

        public virtual void Info()
        {
            string info = $"�{Nomer}: {Author} {Title}. ������ �{Issue}. {NumberOfPages} c.\n�����������: ";
            info += PeriodToVerbal();
            info += $"\n��������� ��� �� ����� �������: {AvgWordsPerPage}";
            MessageBox.Show(info, Title);
        }

        protected string PeriodToVerbal() => PeriodInDays switch
        {
            1 => "�������",
            2 => "����������",
            7 => "���������",
            14 => "������������",
            >= 28 and < 32 => "��������",
            365 | 366 => "������",
            _ => (
                PeriodInDays % 10 == 1
                    ? $"����� {PeriodInDays} ����"
                    : PeriodInDays % 10 >= 2 && PeriodInDays % 10 < 5
                        ? $"���� {PeriodInDays} ��"
                        : $"���� {PeriodInDays} ���"
            )
        };
    }

    public class Newspaper : PeriodicalPublication
    {
        string Headline, Date;

        public Newspaper(int issue, int periodInDays, int numberOfPages, int avgWordsPerPage, string date, string title, string headline, string author)
            : base(issue, periodInDays, numberOfPages, avgWordsPerPage, title, author)
        {
            Date = date;
            Headline = headline;
        }
        public Newspaper() : base()
        {
            Date = "01.01.1970";
            Title = "�������� ������";
            Headline = "����� �����";
        }

        public override int RelativeTimeTillIssueNumber(int nextIssue)
        {
            return Math.Abs((nextIssue - Issue) * PeriodInDays);
        }
        public override int WordCount()
        {
            return NumberOfPages * AvgWordsPerPage;
        }

        public override void Info()
        {
            string info = $"�{Nomer}: {Title} - {Headline}. ������ �{Issue} ({Date}). {NumberOfPages} c.\n�����������: ";
            info += PeriodToVerbal();
            info += $"\n��������� ��� �� ����� �������: {AvgWordsPerPage}";
            MessageBox.Show(info, Headline);
        }
    }

    public class Magazine : PeriodicalPublication
    {
        private int nofImages = 46, nofAds = 8;
        public int NofImages
        {
            get { return nofImages; }
            set { if (value >= 0) nofImages = value; }
        }
        public int NofAds
        {
            get { return nofAds; }
            set { if (value >= 0) nofAds = value; }
        }

        public Magazine(int issue, int periodInDays, int numberOfPages, int avgWordsPerPage, int nOfImages, int nOfAds, string title, string author)
            : base(issue, periodInDays, numberOfPages, avgWordsPerPage, title, author)
        {
            NofImages = nOfImages;
            NofAds = nOfAds;
        }
        public Magazine() : base()
        {
            Title = "��������� ������";
        }

        public override int RelativeTimeTillIssueNumber(int nextIssue)
        {
            return Math.Abs((nextIssue - Issue) * PeriodInDays);
        }
        public override int WordCount()
        {
            return NumberOfPages * AvgWordsPerPage;
        }

        public override void Info()
        {
            string info = $"�{Nomer}: {Title} : ������. ������ �{Issue}. {NumberOfPages} c.\n�����������: ";
            info += PeriodToVerbal();
            info += $"\n��������� ��� �� ����� �������: {AvgWordsPerPage}";
            info += $"\nʳ������ ���������: {NofImages}\nʳ������ ������: {NofAds}";
            MessageBox.Show(info, Title);
        }
    }

    public class JuniorMagazine : Magazine
    {
        private int nofActivities = 6;
        public int NofActivities
        {
            get { return nofActivities; }
            set { if (value >= 0) nofActivities = value; }
        }

        public JuniorMagazine(int issue, int periodInDays, int numberOfPages, int avgWordsPerPage, int nOfImages, int nOfAds, int nOfActivities,
            string title, string author) : base(issue, periodInDays, numberOfPages, avgWordsPerPage, nOfImages, nOfAds, title, author)
        {
            NofActivities = nofActivities;
        }
        public JuniorMagazine() : base()
        {
            Title = "��������� ������� ������";
        }

        public override void Info()
        {
            string info = $"�{Nomer}: {Title} : ������ ��� ����. ������ �{Issue}. {NumberOfPages} c.\n�����������: ";
            info += PeriodToVerbal();
            info += $"\n��������� ��� �� ����� �������: {AvgWordsPerPage}";
            info += $"\nʳ������ ���������: {NofImages}\nʳ������ ������: {NofAds}\nʳ������ �����������: {NofActivities}";
            MessageBox.Show(info, Title);
        }

        public string this[int i]
        {
            get => i switch
            {
                1 => "�������",
                2 => "����������",
                7 => "���������",
                14 => "������������",
                >= 28 and < 32 => "��������",
                365 | 366 => "������",
                _ => (i % 10 == 1
                        ? $"����� {i} ����"
                        : i % 10 >= 2 && i % 10 < 5
                            ? $"���� {i} ��"
                            : $"���� {i} ���")
            };
        }
    }
}